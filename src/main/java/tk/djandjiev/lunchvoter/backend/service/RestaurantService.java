package tk.djandjiev.lunchvoter.backend.service;

import tk.djandjiev.lunchvoter.backend.model.Restaurant;
import tk.djandjiev.lunchvoter.backend.to.RestaurantTO;

import java.util.List;

public interface RestaurantService {
    Restaurant create(Restaurant r);

    void delete(int id);

    Restaurant get(int id);

    List<Restaurant> getAll();

    void update(RestaurantTO restaurantTO);
}
