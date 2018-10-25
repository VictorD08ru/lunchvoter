package tk.djandjiev.lunchvoter.backend.util;

import tk.djandjiev.lunchvoter.backend.model.MenuItem;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static tk.djandjiev.lunchvoter.backend.util.RestaurantTestData.*;
import static tk.djandjiev.lunchvoter.backend.model.AbstractBaseEntity.START_SEQ;

public class MenuTestData {
    public static final int R11_MENU1_ID = START_SEQ + 16;
    public static final int R11_MENU2_ID = START_SEQ + 17;
    public static final int R11_MENU3_ID = START_SEQ + 18;
    public static final int R11_MENU4_ID = START_SEQ + 19;
    public static final int R11_MENU5_ID = START_SEQ + 20;
    public static final int R12_MENU1_ID = START_SEQ + 21;
    public static final int R12_MENU2_ID = START_SEQ + 22;
    public static final int R12_MENU3_ID = START_SEQ + 23;
    public static final int R12_MENU4_ID = START_SEQ + 24;
    public static final int R12_MENU5_ID = START_SEQ + 25;
    public static final int R13_MENU1_ID = START_SEQ + 26;
    public static final int R13_MENU2_ID = START_SEQ + 27;
    public static final int R13_MENU3_ID = START_SEQ + 28;
    public static final int R13_MENU4_ID = START_SEQ + 29;
    public static final int R13_MENU5_ID = START_SEQ + 30;
    public static final int R14_MENU1_ID = START_SEQ + 31;
    public static final int R14_MENU2_ID = START_SEQ + 32;
    public static final int R14_MENU3_ID = START_SEQ + 33;
    public static final int R14_MENU4_ID = START_SEQ + 34;
    public static final int R14_MENU5_ID = START_SEQ + 35;
    public static final int R15_MENU1_ID = START_SEQ + 36;
    public static final int R15_MENU2_ID = START_SEQ + 37;
    public static final int R15_MENU3_ID = START_SEQ + 38;
    public static final int R15_MENU4_ID = START_SEQ + 39;
    public static final int R15_MENU5_ID = START_SEQ + 40;

    public static final MenuItem R_11_MENU_ITEM_1 = new MenuItem(R11_MENU1_ID, "Борщ", 100, LocalDate.now(), RESTAURANT11);
    public static final MenuItem R_11_MENU_ITEM_2 = new MenuItem(R11_MENU2_ID, "Солянка", 110, LocalDate.now(), RESTAURANT11);
    public static final MenuItem R_11_MENU_ITEM_3 = new MenuItem(R11_MENU3_ID, "Котлета", 140, LocalDate.now(), RESTAURANT11);
    public static final MenuItem R_11_MENU_ITEM_4 = new MenuItem(R11_MENU4_ID, "Макароны", 50, LocalDate.now(), RESTAURANT11);
    public static final MenuItem R_11_MENU_ITEM_5 = new MenuItem(R11_MENU5_ID, "Компот", 30, LocalDate.now(), RESTAURANT11);

    public static final MenuItem R_12_MENU_ITEM_1 = new MenuItem(R12_MENU1_ID, "Шаверма", 120, LocalDate.now(), RESTAURANT12);
    public static final MenuItem R_12_MENU_ITEM_2 = new MenuItem(R12_MENU2_ID, "Шаурма", 130, LocalDate.now(), RESTAURANT12);
    public static final MenuItem R_12_MENU_ITEM_3 = new MenuItem(R12_MENU3_ID, "Кебаб", 200, LocalDate.now(), RESTAURANT12);
    public static final MenuItem R_12_MENU_ITEM_4 = new MenuItem(R12_MENU4_ID, "Doner kebab", 300, LocalDate.now(), RESTAURANT12);
    public static final MenuItem R_12_MENU_ITEM_5 = new MenuItem(R12_MENU5_ID, "Попить", 60, LocalDate.now(), RESTAURANT12);

    public static final MenuItem R_13_MENU_ITEM_1 = new MenuItem(R13_MENU1_ID, "Small mac", 120, LocalDate.now(), RESTAURANT13);
    public static final MenuItem R_13_MENU_ITEM_2 = new MenuItem(R13_MENU2_ID, "Small king", 120, LocalDate.now(), RESTAURANT13);
    public static final MenuItem R_13_MENU_ITEM_3 = new MenuItem(R13_MENU3_ID, "Box mister", 150, LocalDate.now(), RESTAURANT13);
    public static final MenuItem R_13_MENU_ITEM_4 = new MenuItem(R13_MENU4_ID, "Картошка фри", 150, LocalDate.now(), RESTAURANT13);
    public static final MenuItem R_13_MENU_ITEM_5 = new MenuItem(R13_MENU5_ID, "Кола 0.5", 100, LocalDate.now(), RESTAURANT13);

    public static final MenuItem R_14_MENU_ITEM_1 = new MenuItem(R14_MENU1_ID, "Блин", 50, LocalDate.now(), RESTAURANT14);
    public static final MenuItem R_14_MENU_ITEM_2 = new MenuItem(R14_MENU2_ID, "Сгущенка", 20, LocalDate.now(), RESTAURANT14);
    public static final MenuItem R_14_MENU_ITEM_3 = new MenuItem(R14_MENU3_ID, "Мясо", 100, LocalDate.now(), RESTAURANT14);
    public static final MenuItem R_14_MENU_ITEM_4 = new MenuItem(R14_MENU4_ID, "Творог", 30, LocalDate.now(), RESTAURANT14);
    public static final MenuItem R_14_MENU_ITEM_5 = new MenuItem(R14_MENU5_ID, "Квас 0,5", 50, LocalDate.now(), RESTAURANT14);

    public static final MenuItem R_15_MENU_ITEM_1 = new MenuItem(R15_MENU1_ID, "Двухэтажный особняк", 300, LocalDate.now(), RESTAURANT15);
    public static final MenuItem R_15_MENU_ITEM_2 = new MenuItem(R15_MENU2_ID, "Хрущевка пятиэтажная", 200, LocalDate.now(), RESTAURANT15);
    public static final MenuItem R_15_MENU_ITEM_3 = new MenuItem(R15_MENU3_ID, "Брежневка девятиэтажная", 240, LocalDate.now(), RESTAURANT15);
    public static final MenuItem R_15_MENU_ITEM_4 = new MenuItem(R15_MENU4_ID, "Домик в деревне", 120, LocalDate.now(), RESTAURANT15);
    public static final MenuItem R_15_MENU_ITEM_5 = new MenuItem(R15_MENU5_ID, "Двухэтажка", 150, LocalDate.now(), RESTAURANT15);

    public static final List<MenuItem> R11_MENU = Arrays.asList(R_11_MENU_ITEM_1, R_11_MENU_ITEM_2, R_11_MENU_ITEM_3, R_11_MENU_ITEM_4, R_11_MENU_ITEM_5);
    public static final List<MenuItem> R12_MENU = Arrays.asList(R_12_MENU_ITEM_1, R_12_MENU_ITEM_2, R_12_MENU_ITEM_3, R_12_MENU_ITEM_4, R_12_MENU_ITEM_5);
    public static final List<MenuItem> R13_MENU = Arrays.asList(R_13_MENU_ITEM_1, R_13_MENU_ITEM_2, R_13_MENU_ITEM_3, R_13_MENU_ITEM_4, R_13_MENU_ITEM_5);
    public static final List<MenuItem> R14_MENU = Arrays.asList(R_14_MENU_ITEM_1, R_14_MENU_ITEM_2, R_14_MENU_ITEM_3, R_14_MENU_ITEM_4, R_14_MENU_ITEM_5);
    public static final List<MenuItem> R15_MENU = Arrays.asList(R_15_MENU_ITEM_1, R_15_MENU_ITEM_2, R_15_MENU_ITEM_3, R_15_MENU_ITEM_4, R_15_MENU_ITEM_5);

    public static void assertMatch(MenuItem actual, MenuItem expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatch(Iterable<MenuItem> actual, Iterable<MenuItem> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }
}
