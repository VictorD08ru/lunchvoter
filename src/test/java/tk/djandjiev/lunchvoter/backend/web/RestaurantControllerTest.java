package tk.djandjiev.lunchvoter.backend.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import tk.djandjiev.lunchvoter.backend.model.Menu;
import tk.djandjiev.lunchvoter.backend.model.Restaurant;
import tk.djandjiev.lunchvoter.backend.service.MenuService;
import tk.djandjiev.lunchvoter.backend.service.RestaurantService;
import tk.djandjiev.lunchvoter.backend.service.VoteService;
import tk.djandjiev.lunchvoter.backend.to.MenuTO;
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
import static tk.djandjiev.lunchvoter.backend.util.UserTestData.*;

class RestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL = "/restaurants/";

    @Autowired
    private RestaurantService rService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private VoteService voteService;

    @Test
    void testGet() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + RESTAURANT11_ID)
                .with(userAuth(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(contentJson(RESTAURANT11)));
    }

    @Test
    void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userAuth(USER0)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANTS)));
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
        RestaurantTO updatedTO = RestaurantUtil.asTo(updated);

        TestUtil.print(mockMvc.perform(put(REST_URL + RESTAURANT11_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updatedTO))
                .with(userAuth(ADMIN)))
                .andExpect(status().isNoContent()));

        assertMatch(rService.get(RESTAURANT11_ID), updated);
    }

    @Test
    void testCreate() throws Exception {
        Restaurant created = new Restaurant("Я новый ресторан");

        ResultActions action = TestUtil.print(mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(created))
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
    void testDeleteMenuItem() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT15_ID + "/menu/" + MenuTestData.R15_MENU1_ID)
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        List<Menu> menuList = new ArrayList<>(MenuTestData.R15_MENU);
        menuList.remove(MenuTestData.R15_MENU1);
        MenuTestData.assertMatch(menuService.getAll(RESTAURANT15_ID), menuList);
    }

    @Test
    void testUpdateMenuItem() throws Exception {
        MenuTO updatedTO = MenuUtil.asTo(MenuTestData.R13_MENU1);
        updatedTO.setDish("Tiny mac");
        updatedTO.setPrice(240);
        Menu updated = MenuUtil.updateFromTo(new Menu(MenuTestData.R13_MENU1), updatedTO);

        TestUtil.print(mockMvc.perform(put(REST_URL + RESTAURANT13_ID + "/menu/" + MenuTestData.R13_MENU1_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updatedTO))
                .with(userAuth(ADMIN)))
                .andExpect(status().isNoContent()));

        MenuTestData.assertMatch(menuService.get(MenuTestData.R13_MENU1_ID), updated);
    }

    @Test
    void testCreateMenuItem() throws Exception {
        Menu created = new Menu("Вареники", 200);

        ResultActions action = TestUtil.print(mockMvc.perform(post(REST_URL + RESTAURANT11_ID + "/menu")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(created))
                .with(userAuth(ADMIN)))
                .andExpect(status().isCreated())
        );

        Menu returned = readFromJson(action, Menu.class);
        created.setId(returned.getId());
        MenuTestData.assertMatch(returned, created);
        List<Menu> expected = new ArrayList<>(MenuTestData.R11_MENU);
        expected.add(created);
        MenuTestData.assertMatch(menuService.getAll(RESTAURANT11_ID), expected);
    }

    @Test
    void testGetMenuItem() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + RESTAURANT11_ID + "/menu/" + MenuTestData.R11_MENU2_ID)
                .with(userAuth(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MenuTestData.contentJson(MenuTestData.R11_MENU2)));
    }

    @Test
    void testGetFullMenu() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + RESTAURANT11_ID + "/menu")
                .with(userAuth(USER0)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.contentJson(MenuTestData.R11_MENU)));
    }
}