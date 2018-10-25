package tk.djandjiev.lunchvoter.backend.util;

import tk.djandjiev.lunchvoter.backend.model.MenuItem;
import tk.djandjiev.lunchvoter.backend.to.MenuItemTO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MenuUtil {

    private MenuUtil() {}

    public static MenuItemTO getTO(MenuItem menuItem) {
        return new MenuItemTO(menuItem.getId(), menuItem.getDish(), menuItem.getPrice(), menuItem.getCookingDate());
    }

    public static MenuItem updateFromTO(MenuItem menuItem, MenuItemTO menuItemTO) {
        menuItem.setDish(menuItemTO.getDish());
        menuItem.setPrice(menuItemTO.getPrice());
        menuItem.setCookingDate(menuItemTO.getCookingDate());
        return menuItem;
    }

    public static List<MenuItemTO> getTOList (Collection<MenuItem> menu) {
        return menu.stream().map(MenuUtil::getTO).collect(Collectors.toList());
    }
}
