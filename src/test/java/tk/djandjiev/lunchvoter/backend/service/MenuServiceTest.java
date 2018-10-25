package tk.djandjiev.lunchvoter.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import tk.djandjiev.lunchvoter.backend.model.MenuItem;
import tk.djandjiev.lunchvoter.backend.to.MenuItemTO;
import tk.djandjiev.lunchvoter.backend.util.MenuUtil;
import tk.djandjiev.lunchvoter.backend.util.exception.ApplicationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tk.djandjiev.lunchvoter.backend.util.MenuTestData.*;
import static tk.djandjiev.lunchvoter.backend.util.RestaurantTestData.*;

class MenuServiceTest extends AbstractCachedServiceTest {

    @Autowired
    private MenuService menuService;

    @BeforeEach
    @Override
    void setUp() throws Exception {
        cacheManager.getCache("menu").clear();
        super.setUp();
    }

    @Test
    void createDuplicate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                menuService.create(new MenuItem("Борщ", 250, LocalDate.now()), RESTAURANT11_ID));
    }

    @Test
    void create() throws Exception {
        MenuItem newMenuItem = new MenuItem("Вареники", 200, LocalDate.now());
        MenuItem created = menuService.create(newMenuItem, RESTAURANT11_ID);
        newMenuItem.setId(created.getId());
        List<MenuItem> expected = new ArrayList<>(R11_MENU);
        expected.add(newMenuItem);
        assertMatch(menuService.getAll(RESTAURANT11_ID, LocalDate.now()), expected);
    }

    @Test
    void delete() throws Exception {
        menuService.delete(R11_MENU2_ID, RESTAURANT11_ID);
        List<MenuItem> expected = new ArrayList<>(R11_MENU);
        expected.remove(R_11_MENU_ITEM_2);
        assertMatch(menuService.getAll(RESTAURANT11_ID, LocalDate.now()), expected);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(ApplicationException.class, () -> menuService.delete(15, RESTAURANT13_ID));
    }

    @Test
    void get() throws Exception {
        assertMatch(menuService.get(R11_MENU1_ID), R_11_MENU_ITEM_1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(ApplicationException.class, () -> menuService.get(11));
    }

    @Test
    void update() throws Exception {
        MenuItemTO updated = MenuUtil.getTO(R_13_MENU_ITEM_1);
        updated.setDish("Tiny mac");
        updated.setPrice(240);
        MenuItem menuItem = MenuUtil.updateFromTO(new MenuItem(R_13_MENU_ITEM_1), updated);
        menuService.update(updated, RESTAURANT13_ID);
        assertMatch(menuService.getAll(RESTAURANT13_ID, LocalDate.now()), Arrays.asList(R_13_MENU_ITEM_2, R_13_MENU_ITEM_3, R_13_MENU_ITEM_4, R_13_MENU_ITEM_5, menuItem));
    }

    @Test
    void getAll() throws Exception {
        assertMatch(menuService.getAll(RESTAURANT11_ID, LocalDate.now()), R11_MENU);
        assertMatch(menuService.getAll(RESTAURANT12_ID, LocalDate.now()), R12_MENU);
        assertMatch(menuService.getAll(RESTAURANT13_ID, LocalDate.now()), R13_MENU);
        assertMatch(menuService.getAll(RESTAURANT14_ID, LocalDate.now()), R14_MENU);
        assertMatch(menuService.getAll(RESTAURANT15_ID, LocalDate.now()), R15_MENU);
    }
}