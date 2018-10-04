package tk.djandjiev.lunchvoter.backend.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.djandjiev.lunchvoter.backend.model.User;
import tk.djandjiev.lunchvoter.backend.to.UserTO;
import tk.djandjiev.lunchvoter.backend.util.JsonUtil;
import tk.djandjiev.lunchvoter.backend.util.TestUtil;
import tk.djandjiev.lunchvoter.backend.util.UserUtil;
import tk.djandjiev.lunchvoter.backend.util.exception.ErrorType;
import tk.djandjiev.lunchvoter.backend.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tk.djandjiev.lunchvoter.backend.util.TestUtil.userHttpBasic;
import static tk.djandjiev.lunchvoter.backend.util.UserTestData.*;
import static tk.djandjiev.lunchvoter.backend.web.ExceptionInfoHandler.*;
import static tk.djandjiev.lunchvoter.backend.web.user.ProfileController.REST_URL;

class ProfileControllerTest extends AbstractControllerTest {

    @Test
    void testGet() throws Exception {
        TestUtil.print(
                mockMvc.perform(get(REST_URL)
                        .with(userHttpBasic(USER1)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(contentJson(USER1))
        );
    }

    @Test
    void testGetUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .with(userHttpBasic(USER0)))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN, USER1, USER2, USER3, USER4, USER5, USER6, USER7, USER8, USER9);
    }

    @Test
    void testUpdate() throws Exception {
        UserTO updatedTo = new UserTO(null, "newName", "newemail@ya.ru", "newPassword");

        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER5))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(userService.getByEmail("newemail@ya.ru"), UserUtil.updateFromTo(new User(USER5), updatedTo));
    }

    @Test
    void testUpdateInvalid() throws Exception {
        UserTO updatedTo = new UserTO(null, null, "password", null);

        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void testDuplicate() throws Exception {
        UserTO updatedTo = new UserTO(null, "newName", "admin@gmail.com", "newPassword");

        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isConflict())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(jsonMessage("$.details", EXCEPTION_DUPLICATE_EMAIL))
                .andDo(print());
    }
}