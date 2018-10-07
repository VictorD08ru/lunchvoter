package tk.djandjiev.lunchvoter.backend.util;

import tk.djandjiev.lunchvoter.backend.model.Menu;

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

    public static final Menu R11_MENU1 = new Menu(R11_MENU1_ID, "Борщ", 100, RESTAURANT11);
    public static final Menu R11_MENU2 = new Menu(R11_MENU2_ID, "Солянка", 110, RESTAURANT11);
    public static final Menu R11_MENU3 = new Menu(R11_MENU3_ID, "Котлета", 140, RESTAURANT11);
    public static final Menu R11_MENU4 = new Menu(R11_MENU4_ID, "Макароны", 50, RESTAURANT11);
    public static final Menu R11_MENU5 = new Menu(R11_MENU5_ID, "Компот", 30, RESTAURANT11);

    public static final Menu R12_MENU1 = new Menu(R12_MENU1_ID, "Шаверма", 120, RESTAURANT12);
    public static final Menu R12_MENU2 = new Menu(R12_MENU2_ID, "Шаурма", 130, RESTAURANT12);
    public static final Menu R12_MENU3 = new Menu(R12_MENU3_ID, "Кебаб", 200, RESTAURANT12);
    public static final Menu R12_MENU4 = new Menu(R12_MENU4_ID, "Doner kebab", 300, RESTAURANT12);
    public static final Menu R12_MENU5 = new Menu(R12_MENU5_ID, "Попить", 60, RESTAURANT12);

    public static final Menu R13_MENU1 = new Menu(R13_MENU1_ID, "Small mac", 120, RESTAURANT13);
    public static final Menu R13_MENU2 = new Menu(R13_MENU2_ID, "Small king", 120, RESTAURANT13);
    public static final Menu R13_MENU3 = new Menu(R13_MENU3_ID, "Box mister", 150, RESTAURANT13);
    public static final Menu R13_MENU4 = new Menu(R13_MENU4_ID, "Картошка фри", 150, RESTAURANT13);
    public static final Menu R13_MENU5 = new Menu(R13_MENU5_ID, "Кола 0.5", 100, RESTAURANT13);

    public static final Menu R14_MENU1 = new Menu(R14_MENU1_ID, "Блин", 50, RESTAURANT14);
    public static final Menu R14_MENU2 = new Menu(R14_MENU2_ID, "Сгущенка", 20, RESTAURANT14);
    public static final Menu R14_MENU3 = new Menu(R14_MENU3_ID, "Мясо", 100, RESTAURANT14);
    public static final Menu R14_MENU4 = new Menu(R14_MENU4_ID, "Творог", 30, RESTAURANT14);
    public static final Menu R14_MENU5 = new Menu(R14_MENU5_ID, "Квас 0,5", 50, RESTAURANT14);

    public static final Menu R15_MENU1 = new Menu(R15_MENU1_ID, "Двухэтажный особняк", 300, RESTAURANT15);
    public static final Menu R15_MENU2 = new Menu(R15_MENU2_ID, "Хрущевка пятиэтажная", 200, RESTAURANT15);
    public static final Menu R15_MENU3 = new Menu(R15_MENU3_ID, "Брежневка девятиэтажная", 240, RESTAURANT15);
    public static final Menu R15_MENU4 = new Menu(R15_MENU4_ID, "Домик в деревне", 120, RESTAURANT15);
    public static final Menu R15_MENU5 = new Menu(R15_MENU5_ID, "Двухэтажка", 150, RESTAURANT15);

    public static final List<Menu> R11_MENU = Arrays.asList(R11_MENU1, R11_MENU2, R11_MENU3, R11_MENU4, R11_MENU5);
    public static final List<Menu> R12_MENU = Arrays.asList(R12_MENU1, R12_MENU2, R12_MENU3, R12_MENU4, R12_MENU5);
    public static final List<Menu> R13_MENU = Arrays.asList(R13_MENU1, R13_MENU2, R13_MENU3, R13_MENU4, R13_MENU5);
    public static final List<Menu> R14_MENU = Arrays.asList(R14_MENU1, R14_MENU2, R14_MENU3, R14_MENU4, R14_MENU5);
    public static final List<Menu> R15_MENU = Arrays.asList(R15_MENU1, R15_MENU2, R15_MENU3, R15_MENU4, R15_MENU5);

    public static void assertMatch(Menu actual, Menu expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatch(Iterable<Menu> actual, Iterable<Menu> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }
}
