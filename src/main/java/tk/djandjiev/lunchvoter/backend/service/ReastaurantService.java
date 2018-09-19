package tk.djandjiev.lunchvoter.backend.service;

import tk.djandjiev.lunchvoter.backend.model.Restaurant;

import java.util.List;

public interface ReastaurantService {
    Restaurant create(Restaurant r);

    void delete(int id);

    Restaurant get(int id);

    void update(Restaurant r);

    List<Restaurant> getAll();
}
