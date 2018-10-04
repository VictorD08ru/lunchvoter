package tk.djandjiev.lunchvoter.backend.service;

import tk.djandjiev.lunchvoter.backend.model.Menu;
import tk.djandjiev.lunchvoter.backend.to.MenuTO;

import java.util.List;

public interface MenuService {
    Menu create(Menu menu, int restaurantId);

    void delete(int id);

    Menu get(int id);

    void update(MenuTO menuTO);

    List<Menu> getAll(int restaurantId);
}
