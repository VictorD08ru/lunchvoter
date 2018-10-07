package tk.djandjiev.lunchvoter.backend.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import tk.djandjiev.lunchvoter.backend.model.Restaurant;
import tk.djandjiev.lunchvoter.backend.service.RestaurantService;
import tk.djandjiev.lunchvoter.backend.service.VoteService;
import tk.djandjiev.lunchvoter.backend.to.RestaurantTO;
import tk.djandjiev.lunchvoter.backend.util.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tk.djandjiev.lunchvoter.backend.util.RestaurantTestData.*;
import static tk.djandjiev.lunchvoter.backend.util.TestUtil.readFromJson;
import static tk.djandjiev.lunchvoter.backend.util.TestUtil.userAuth;
import static tk.djandjiev.lunchvoter.backend.util.TestUtil.userHttpBasic;
import static tk.djandjiev.lunchvoter.backend.util.UserTestData.ADMIN;
import static tk.djandjiev.lunchvoter.backend.util.UserTestData.USER1;

class RestaurantControllerTest extends AbstractControllerTest {
    static final String REST_URL = RestaurantController.RESTAURANT_URL + "/";

    @Autowired
    private RestaurantService rService;

    @Autowired
    private VoteService voteService;

    @Test
    void testGet() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + RESTAURANT11_ID)
                .with(userAuth(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(TestUtil.contentJson(RestaurantUtil.getTO(RESTAURANT11))));
    }

    @Test
    void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userAuth(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.contentJson(RestaurantUtil.getTOList(RESTAURANTS))));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT15_ID)
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        List<Restaurant> restaurants = new ArrayList<>(RESTAURANTS);
        restaurants.remove(RESTAURANT15);
        assertMatch(rService.getAll(), restaurants);
    }

    @Test
    void testUpdate() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT11);
        updated.setName("Столовая №6");
        RestaurantTO updatedTO = RestaurantUtil.getTO(updated);

        TestUtil.print(mockMvc.perform(put(REST_URL + RESTAURANT11_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updatedTO))
                .with(userAuth(ADMIN)))
                .andExpect(status().isNoContent()));

        assertMatch(rService.get(RESTAURANT11_ID), updated);
    }

    @Test
    void testCreate() throws Exception {
        Restaurant created = new Restaurant(null,"Я новый ресторан");
        RestaurantTO createdTO = RestaurantUtil.getTO(created);

        ResultActions action = TestUtil.print(mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(createdTO))
                .with(userAuth(ADMIN)))
                .andExpect(status().isCreated()));

        Restaurant returned = readFromJson(action, Restaurant.class);
        created.setId(returned.getId());
        assertMatch(returned, created);
        List<Restaurant> expected = new ArrayList<>(RESTAURANTS);
        expected.add(created);
        assertMatch(rService.getAll(), expected);
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userHttpBasic(USER1)))
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

    //access denied without admin's role
    @Test
    void testAccessDenied() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT15_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }

    @Test
    void testGetVotes() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + RESTAURANT11_ID + "/votes")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.contentJson(VoteUtil.getTOList(voteService.getAll(RESTAURANT11_ID)))));
    }
}