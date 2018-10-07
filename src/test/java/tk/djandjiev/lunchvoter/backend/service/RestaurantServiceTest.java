package tk.djandjiev.lunchvoter.backend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import tk.djandjiev.lunchvoter.backend.model.Restaurant;
import tk.djandjiev.lunchvoter.backend.to.RestaurantTO;
import tk.djandjiev.lunchvoter.backend.util.RestaurantUtil;
import tk.djandjiev.lunchvoter.backend.util.exception.ApplicationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tk.djandjiev.lunchvoter.backend.util.RestaurantTestData.*;

class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService rService;

    @Test
    void create() throws Exception {
        Restaurant restaurant = new Restaurant(null,"Я новый ресторан");
        Restaurant created = rService.create(restaurant);
        restaurant.setId(created.getId());
        List<Restaurant> expected = new ArrayList<>(RESTAURANTS);
        expected.add(restaurant);
        assertMatch(created, restaurant);
        assertMatch(rService.getAll(), expected);
    }

    @Test
    void createDuplicate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                rService.create(new Restaurant(null,"Shaverma Cafe")));
    }

    @Test
    void delete() throws Exception {
        List<Restaurant> expected = new ArrayList<>(RESTAURANTS);
        rService.delete(RESTAURANT15_ID);
        expected.remove(RESTAURANT15);
        assertMatch(rService.getAll(), expected);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(ApplicationException.class, () -> rService.delete(10));
    }

    @Test
    void get() throws Exception {
        assertMatch(rService.get(RESTAURANT12_ID), RESTAURANT12);
    }

    @Test
    void getNotFound() throws Exception {
        Assertions.assertThrows(ApplicationException.class, () -> rService.get(10));
    }

    @Test
    void update() throws Exception {
        RestaurantTO updated = RestaurantUtil.getTO(RESTAURANT11);
        updated.setName("Столовая №6");
        Restaurant r = RestaurantUtil.updateFromTo(new Restaurant(RESTAURANT11), updated);
        rService.update(updated);
        assertMatch(rService.getAll(), RESTAURANT12, RESTAURANT14, RESTAURANT13,  RESTAURANT15, r);
    }

    @Test
    void getAll() throws Exception {
        assertMatch(rService.getAll(), RESTAURANTS);
    }
}