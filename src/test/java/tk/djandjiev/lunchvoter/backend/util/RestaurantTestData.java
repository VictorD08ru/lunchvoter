package tk.djandjiev.lunchvoter.backend.util;

import org.springframework.test.web.servlet.ResultMatcher;
import tk.djandjiev.lunchvoter.backend.model.Restaurant;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static tk.djandjiev.lunchvoter.backend.model.AbstractBaseEntity.START_SEQ;
import static tk.djandjiev.lunchvoter.backend.util.JsonUtil.writeIgnoreProps;

public class RestaurantTestData {
    public static final int RESTAURANT11_ID = START_SEQ + 11;
    public static final int RESTAURANT12_ID = START_SEQ + 12;
    public static final int RESTAURANT13_ID = START_SEQ + 13;
    public static final int RESTAURANT14_ID = START_SEQ + 14;
    public static final int RESTAURANT15_ID = START_SEQ + 15;

    public static final Restaurant RESTAURANT11 = new Restaurant(RESTAURANT11_ID, "Столовая №1");
    public static final Restaurant RESTAURANT12 = new Restaurant(RESTAURANT12_ID, "Shaverma Cafe");
    public static final Restaurant RESTAURANT13 = new Restaurant(RESTAURANT13_ID, "Бургерная");
    public static final Restaurant RESTAURANT14 = new Restaurant(RESTAURANT14_ID, "Блинная");
    public static final Restaurant RESTAURANT15 = new Restaurant(RESTAURANT15_ID, "Едим дома");

    public static final List<Restaurant> RESTAURANTS = Arrays.asList(RESTAURANT12, RESTAURANT14, RESTAURANT13,  RESTAURANT15, RESTAURANT11);

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "menu", "votes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("menu", "votes").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static ResultMatcher contentJson(Restaurant expected) {
        return content().json(writeIgnoreProps(expected, "menu", "votes"));
    }

    public static ResultMatcher contentJson(Collection<Restaurant> expected) {
        return content().json(writeIgnoreProps(expected, "menu", "votes"));
    }
}
