package tk.djandjiev.lunchvoter.backend.util;

import tk.djandjiev.lunchvoter.backend.model.Restaurant;
import tk.djandjiev.lunchvoter.backend.to.RestaurantTO;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtil {
    private RestaurantUtil() {}

    public static Restaurant createNewFromTO(RestaurantTO rTO) {
        return new Restaurant(null, rTO.getName());
    }

    public static RestaurantTO getTO(Restaurant r) {
        return new RestaurantTO(r.getId(), r.getName());
    }

    public static Restaurant updateFromTo(Restaurant r, RestaurantTO rTO) {
        r.setName(rTO.getName());
        return r;
    }

    public static List<RestaurantTO> getTOList(List<Restaurant> rList) {
        return rList.stream().map(RestaurantUtil::getTO).collect(Collectors.toList());
    }
}
