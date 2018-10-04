package tk.djandjiev.lunchvoter.backend.util;
import org.springframework.test.web.servlet.ResultMatcher;
import tk.djandjiev.lunchvoter.backend.model.Restaurant;
import tk.djandjiev.lunchvoter.backend.model.Vote;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static tk.djandjiev.lunchvoter.backend.util.JsonUtil.writeIgnoreProps;
import static tk.djandjiev.lunchvoter.backend.util.RestaurantTestData.*;
import static tk.djandjiev.lunchvoter.backend.util.UserTestData.*;
import static tk.djandjiev.lunchvoter.backend.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.*;

public class VoteTestData {
    public static final int VOTE1_ID = START_SEQ + 41;
    public static final int VOTE2_ID = START_SEQ + 42;
    public static final int VOTE3_ID = START_SEQ + 43;
    public static final int VOTE4_ID = START_SEQ + 44;
    public static final int VOTE5_ID = START_SEQ + 45;
    public static final int VOTE6_ID = START_SEQ + 46;
    public static final int VOTE7_ID = START_SEQ + 47;
    public static final int VOTE8_ID = START_SEQ + 48;
    public static final int VOTE9_ID = START_SEQ + 49;
    public static final int VOTE10_ID = START_SEQ + 50;

    public static final Vote VOTE1 = new Vote(VOTE1_ID, ADMIN, RESTAURANT15);
    public static final Vote VOTE2 = new Vote(VOTE2_ID, USER1, RESTAURANT11);
    public static final Vote VOTE3 = new Vote(VOTE3_ID, USER2, RESTAURANT11);
    public static final Vote VOTE4 = new Vote(VOTE4_ID, USER3, RESTAURANT11);
    public static final Vote VOTE5 = new Vote(VOTE5_ID, USER4, RESTAURANT11);
    public static final Vote VOTE6 = new Vote(VOTE6_ID, USER5, RESTAURANT11);
    public static final Vote VOTE7 = new Vote(VOTE7_ID, USER6, RESTAURANT14);
    public static final Vote VOTE8 = new Vote(VOTE8_ID, USER7, RESTAURANT14);
    public static final Vote VOTE9 = new Vote(VOTE9_ID, USER8, RESTAURANT14);
    public static final Vote VOTE10 = new Vote(VOTE10_ID, USER9, RESTAURANT13);

    public static final List<Vote> VOTES = Arrays.asList(VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6, VOTE7, VOTE8, VOTE9, VOTE10);

    public static Vote getCreated() {
        return new Vote(null, LocalDate.now(), USER0, RESTAURANT13);
    }

    public static List<Vote> getVotesForRestaurant(Collection<Vote> list, int rId) {
        return list.stream().filter(v -> v.getRestaurant().getId() == rId).collect(Collectors.toList());
    }

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user", "restaurant");
    }

    public static void assertMatch(Map<Restaurant, Long> actual, Map<Restaurant, Long> expected) {
        assertThat(actual).usingDefaultComparator().isEqualTo(expected);
    }

    public static void assertMatch(Collection<Vote> actual, Collection<Vote> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user", "restaurant").isEqualTo(expected);
    }

    public static void assertMatch(Collection<Vote> actual, Vote... expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user", "restaurant").isEqualTo(Arrays.asList(expected));
    }
}
