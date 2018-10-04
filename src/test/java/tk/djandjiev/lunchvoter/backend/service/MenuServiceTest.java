package tk.djandjiev.lunchvoter.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import tk.djandjiev.lunchvoter.backend.model.Menu;
import tk.djandjiev.lunchvoter.backend.to.MenuTO;
import tk.djandjiev.lunchvoter.backend.util.JpaUtil;
import tk.djandjiev.lunchvoter.backend.util.MenuUtil;
import tk.djandjiev.lunchvoter.backend.util.exception.ApplicationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tk.djandjiev.lunchvoter.backend.util.MenuTestData.*;
import static tk.djandjiev.lunchvoter.backend.util.RestaurantTestData.*;

class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired(required = false)
    private JpaUtil jpaUtil;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("menu").clear();
        if (jpaUtil == null) {
            jpaUtil.clear2ndLevelHibernateCache();
        }
    }

    @Test
    void create() throws Exception {
        Menu newMenu = new Menu("Вареники", 200);
        Menu created = menuService.create(newMenu, RESTAURANT11_ID);
        newMenu.setId(created.getId());
        List<Menu> expected = new ArrayList<>(R11_MENU);
        expected.add(newMenu);
        assertMatch(menuService.getAll(RESTAURANT11_ID), expected);
    }

    @Test
    void delete() throws Exception {
        menuService.delete(R11_MENU2_ID);
        List<Menu> expected = new ArrayList<>(R11_MENU);
        expected.remove(R11_MENU2);
        assertMatch(menuService.getAll(RESTAURANT11_ID), expected);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(ApplicationException.class, () -> menuService.delete(15));
    }

    @Test
    void get() throws Exception {
        assertMatch(menuService.get(R11_MENU1_ID), R11_MENU1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(ApplicationException.class, () -> menuService.get(11));
    }

    @Test
    void update() throws Exception {
        MenuTO updated = MenuUtil.asTo(R13_MENU1);
        updated.setDish("Tiny mac");
        updated.setPrice(240);
        Menu menu = MenuUtil.updateFromTo(new Menu(R13_MENU1), updated);
        menuService.update(updated);
        assertMatch(menuService.getAll(RESTAURANT13_ID), Arrays.asList(R13_MENU2, R13_MENU3, R13_MENU4, R13_MENU5, menu));
    }

    @Test
    void getAll() throws Exception {
        assertMatch(menuService.getAll(RESTAURANT11_ID), R11_MENU);
        assertMatch(menuService.getAll(RESTAURANT12_ID), R12_MENU);
        assertMatch(menuService.getAll(RESTAURANT13_ID), R13_MENU);
        assertMatch(menuService.getAll(RESTAURANT14_ID), R14_MENU);
        assertMatch(menuService.getAll(RESTAURANT15_ID), R15_MENU);
    }
}