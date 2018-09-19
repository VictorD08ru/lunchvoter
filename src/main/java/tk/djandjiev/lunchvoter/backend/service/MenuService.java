package tk.djandjiev.lunchvoter.backend.service;

import tk.djandjiev.lunchvoter.backend.model.Menu;

import java.util.List;

public interface MenuService {
    Menu create(Menu menu, int restaurantId);

    void delete(int id);

    Menu get(int id);

    void update(Menu menu, int restaurantId);

    List<Menu> getAll(int restaurantId);

    List<Menu> getAllForCurrentDay(int restaurantId);
}
