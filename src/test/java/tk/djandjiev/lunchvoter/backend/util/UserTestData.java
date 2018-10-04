package tk.djandjiev.lunchvoter.backend.util;

import org.springframework.test.web.servlet.ResultMatcher;
import tk.djandjiev.lunchvoter.backend.model.Role;
import tk.djandjiev.lunchvoter.backend.model.User;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static tk.djandjiev.lunchvoter.backend.model.AbstractBaseEntity.START_SEQ;
import static tk.djandjiev.lunchvoter.backend.util.JsonUtil.writeIgnoreProps;


public class UserTestData {
    public static final int USER0_ID = START_SEQ;
    public static final int USER1_ID = START_SEQ + 1;
    public static final int USER2_ID = START_SEQ + 2;
    public static final int USER3_ID = START_SEQ + 3;
    public static final int USER4_ID = START_SEQ + 4;
    public static final int USER5_ID = START_SEQ + 5;
    public static final int USER6_ID = START_SEQ + 6;
    public static final int USER7_ID = START_SEQ + 7;
    public static final int USER8_ID = START_SEQ + 8;
    public static final int USER9_ID = START_SEQ + 9;
    public static final int ADMIN_ID = START_SEQ + 10;

    public static final User USER0 = new User(USER0_ID, "User0", "user0@yandex.ru", "password0", Role.ROLE_USER);
    public static final User USER1 = new User(USER1_ID, "User1", "user1@yandex.ru", "password1", Role.ROLE_USER);
    public static final User USER2 = new User(USER2_ID, "User2", "user2@yandex.ru", "password2", Role.ROLE_USER);
    public static final User USER3 = new User(USER3_ID, "User3", "user3@yandex.ru", "password3", Role.ROLE_USER);
    public static final User USER4 = new User(USER4_ID, "User4", "user4@yandex.ru", "password4", Role.ROLE_USER);
    public static final User USER5 = new User(USER5_ID, "User5", "user5@yandex.ru", "password5", Role.ROLE_USER);
    public static final User USER6 = new User(USER6_ID, "User6", "user6@yandex.ru", "password6", Role.ROLE_USER);
    public static final User USER7 = new User(USER7_ID, "User7", "user7@yandex.ru", "password7", Role.ROLE_USER);
    public static final User USER8 = new User(USER8_ID, "User8", "user8@yandex.ru", "password8", Role.ROLE_USER);
    public static final User USER9 = new User(USER9_ID, "User9", "user9@yandex.ru", "password9", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static final List<User> USERS = Arrays.asList(ADMIN, USER0, USER1, USER2, USER3, USER4, USER5, USER6, USER7, USER8, USER9);

    static {
        USERS.sort(Comparator.comparing(User::getName).thenComparing(User::getEmail));
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "password");
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "password").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static ResultMatcher contentJson(User... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "registered", "password"));
    }

    public static ResultMatcher contentJson(User expected) {
        return content().json(writeIgnoreProps(expected, "registered", "password"));
    }

    public static String jsonWithPassword(User user, String password) {
        return JsonUtil.writeAdditionProps(user, "password", password);
    }
}