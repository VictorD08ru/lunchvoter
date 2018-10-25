package tk.djandjiev.lunchvoter.backend.service;

import tk.djandjiev.lunchvoter.backend.model.MenuItem;
import tk.djandjiev.lunchvoter.backend.to.MenuItemTO;

import java.time.LocalDate;
import java.util.List;

public interface MenuService {
    MenuItem create(MenuItem menuItem, int restaurantId);

    void delete(int id, int restaurantId);

    MenuItem get(int id);

    void update(MenuItemTO menuItemTO, int restaurantId);

    List<MenuItem> getAll(int restaurantId, LocalDate cookingDate);
}
