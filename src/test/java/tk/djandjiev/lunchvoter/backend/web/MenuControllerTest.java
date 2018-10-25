package tk.djandjiev.lunchvoter.backend.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import tk.djandjiev.lunchvoter.backend.model.MenuItem;
import tk.djandjiev.lunchvoter.backend.service.MenuService;
import tk.djandjiev.lunchvoter.backend.to.MenuItemTO;
import tk.djandjiev.lunchvoter.backend.util.JsonUtil;
import tk.djandjiev.lunchvoter.backend.util.MenuUtil;
import tk.djandjiev.lunchvoter.backend.util.TestUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tk.djandjiev.lunchvoter.backend.util.MenuTestData.*;
import static tk.djandjiev.lunchvoter.backend.util.RestaurantTestData.RESTAURANT11_ID;
import static tk.djandjiev.lunchvoter.backend.util.RestaurantTestData.RESTAURANT13_ID;
import static tk.djandjiev.lunchvoter.backend.util.RestaurantTestData.RESTAURANT15_ID;
import static tk.djandjiev.lunchvoter.backend.util.TestUtil.readFromJson;
import static tk.djandjiev.lunchvoter.backend.util.TestUtil.userAuth;
import static tk.djandjiev.lunchvoter.backend.util.TestUtil.userHttpBasic;
import static tk.djandjiev.lunchvoter.backend.util.UserTestData.ADMIN;
import static tk.djandjiev.lunchvoter.backend.util.UserTestData.USER0;
import static tk.djandjiev.lunchvoter.backend.util.UserTestData.USER1;
import static tk.djandjiev.lunchvoter.backend.web.RestaurantControllerTest.REST_URL;

class MenuControllerTest extends AbstractCachedControllerTest {

    @Autowired
    private MenuService menuService;

    @BeforeEach
    @Override
    void setUp() {
        cacheManager.getCache("menu").clear();
        super.setUp();
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT15_ID + "/menu/" + R15_MENU1_ID)
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        List<MenuItem> menu = new ArrayList<>(R15_MENU);
        menu.remove(R_15_MENU_ITEM_1);
        assertMatch(menuService.getAll(RESTAURANT15_ID, LocalDate.now()), menu);
    }

    @Test
    void testUpdate() throws Exception {
        MenuItemTO updatedTO = MenuUtil.getTO(R_13_MENU_ITEM_1);
        updatedTO.setDish("Tiny mac");
        updatedTO.setPrice(240);
        MenuItem updated = MenuUtil.updateFromTO(new MenuItem(R_13_MENU_ITEM_1), updatedTO);

        TestUtil.print(mockMvc.perform(put(REST_URL + RESTAURANT13_ID + "/menu/" + R13_MENU1_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updatedTO))
                .with(userAuth(ADMIN)))
                .andExpect(status().isNoContent()));

        assertMatch(menuService.get(R13_MENU1_ID), updated);
    }

    @Test
    void testCreate() throws Exception {
        MenuItem created = new MenuItem("Вареники", 200, LocalDate.now());

        ResultActions action = TestUtil.print(mockMvc.perform(post(REST_URL + RESTAURANT11_ID + "/menu")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(created))
                .with(userAuth(ADMIN)))
                .andExpect(status().isCreated())
        );

        MenuItem returned = readFromJson(action, MenuItem.class);
        created.setId(returned.getId());
        assertMatch(returned, created);
        List<MenuItem> expected = new ArrayList<>(R11_MENU);
        expected.add(created);
        assertMatch(menuService.getAll(RESTAURANT11_ID, LocalDate.now()), expected);
    }

    @Test
    void testGet() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + RESTAURANT11_ID + "/menu/" + R11_MENU2_ID)
                .with(userAuth(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(TestUtil.contentJson(MenuUtil.getTO(R_11_MENU_ITEM_2))));
    }

    @Test
    void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + RESTAURANT11_ID + "/menu")
                .with(userAuth(USER0)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestUtil.contentJson(MenuUtil.getTOList(R11_MENU))));
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT15_ID + "/menu/" + 1)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT15_ID + "/menu/" + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    //access denied without admin's role
    @Test
    void testAccessDenied() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT15_ID + "/menu/" + R15_MENU1_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().is5xxServerError())
                .andDo(print());
    }
}