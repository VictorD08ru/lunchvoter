package tk.djandjiev.lunchvoter.backend.web.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.djandjiev.lunchvoter.backend.model.Role;
import tk.djandjiev.lunchvoter.backend.model.User;
import tk.djandjiev.lunchvoter.backend.util.JsonUtil;
import tk.djandjiev.lunchvoter.backend.util.TestUtil;
import tk.djandjiev.lunchvoter.backend.util.UserTestData;
import tk.djandjiev.lunchvoter.backend.util.exception.ErrorType;
import tk.djandjiev.lunchvoter.backend.web.AbstractCachedControllerTest;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tk.djandjiev.lunchvoter.backend.util.TestUtil.userHttpBasic;
import static tk.djandjiev.lunchvoter.backend.util.UserTestData.*;
import static tk.djandjiev.lunchvoter.backend.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL;

class AdminControllerTest extends AbstractCachedControllerTest {

    private static final String REST_URL = AdminController.REST_URL + '/';

    @BeforeEach
    void setUp() {
        cacheManager.getCache("users").clear();
        if (jpaUtil == null) {
            jpaUtil.clear2ndLevelHibernateCache();
        }
    }

    @Test
    void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN, USER0, USER1, USER2, USER3, USER4, USER5, USER6, USER7, USER8, USER9)));
    }

    @Test
    void testGet() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + ADMIN_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(UserTestData.contentJson(ADMIN)));
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void testCreate() throws Exception {
        User expected = new User(null, "New", "new@gmail.com", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(jsonWithPassword(expected, "newPass")))
                .andExpect(status().isCreated());

        User returned = TestUtil.readFromJson(action, User.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(userService.getAll(), ADMIN, expected, USER0, USER1, USER2, USER3, USER4, USER5, USER6, USER7, USER8, USER9);
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN, USER0, USER2, USER3, USER4, USER5, USER6, USER7, USER8, USER9);
    }

    @Test
    void testUpdate() throws Exception {
        User updated = new User(USER0);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        mockMvc.perform(put(REST_URL + USER0_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(jsonWithPassword(updated, USER0.getPassword())))
                .andExpect(status().isNoContent());

        assertMatch(userService.get(USER0_ID), updated);
    }

    @Test
    void testGetByMail() throws Exception {
        mockMvc.perform(get(REST_URL + "by?email=" + ADMIN.getEmail())
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN));
    }

    @Test
    void testCreateInvalid() throws Exception {
        User expected = new User(null, null, "", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void testUpdateInvalid() throws Exception {
        User updated = new User(USER2);
        updated.setName("");
        mockMvc.perform(put(REST_URL + USER2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void testUpdateDuplicate() throws Exception {
        User updated = new User(USER1);
        updated.setEmail("admin@gmail.com");
        mockMvc.perform(put(REST_URL + USER1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(jsonWithPassword(updated, "password")))
                .andExpect(status().isConflict())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(jsonMessage("$.details", EXCEPTION_DUPLICATE_EMAIL))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void testCreateDuplicate() throws Exception {
        User expected = new User(null, "New", "user1@yandex.ru", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(jsonWithPassword(expected, "newPass")))
                .andExpect(status().isConflict())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(jsonMessage("$.details", EXCEPTION_DUPLICATE_EMAIL));

    }
}