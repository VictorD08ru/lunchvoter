package tk.djandjiev.lunchvoter.backend.util;

import tk.djandjiev.lunchvoter.backend.model.Menu;
import tk.djandjiev.lunchvoter.backend.model.Restaurant;
import tk.djandjiev.lunchvoter.backend.model.Vote;
import tk.djandjiev.lunchvoter.backend.to.MenuTO;
import tk.djandjiev.lunchvoter.backend.to.RestaurantTO;
import tk.djandjiev.lunchvoter.backend.to.VoteTO;

import java.util.Collections;
import java.util.List;

public class RestaurantUtil {
    private RestaurantUtil() {}

    public static RestaurantTO asTo(Restaurant r) {
        List<MenuTO> menuList = Collections.emptyList();
        List<VoteTO> votes = Collections.emptyList();
        return new RestaurantTO(r.getId(), r.getName(), menuList, votes);
    }

    public static RestaurantTO asTOWithVotes(Restaurant r, List<Menu> menuList, List<Vote> votes) {
        return new RestaurantTO(r.getId(), r.getName(), MenuUtil.getTOList(menuList), VoteUtil.getTOList(votes));
    }

    public static Restaurant updateFromTo(Restaurant r, RestaurantTO rTO) {
        r.setName(rTO.getName());
        return r;
    }
}
