package tk.djandjiev.lunchvoter.backend.util;

import tk.djandjiev.lunchvoter.backend.model.Menu;
import tk.djandjiev.lunchvoter.backend.to.MenuTO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MenuUtil {

    private MenuUtil() {}

    public static MenuTO getTO(Menu menu) {
        return new MenuTO(menu.getId(), menu.getDish(), menu.getPrice());
    }

    public static Menu updateFromTo(Menu menu, MenuTO menuTO) {
        menu.setDish(menuTO.getDish());
        menu.setPrice(menuTO.getPrice());
        return menu;
    }

    public static List<MenuTO> getTOList (Collection<Menu> menuList) {
        return menuList.stream().map(MenuUtil::getTO).collect(Collectors.toList());
    }
}
