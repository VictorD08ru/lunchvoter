package tk.djandjiev.lunchvoter.backend.service;

import tk.djandjiev.lunchvoter.backend.model.Restaurant;
import tk.djandjiev.lunchvoter.backend.model.Vote;

import java.util.List;
import java.util.Map;

public interface VoteService {
    Vote create(Vote vote, int userId, int restaurantId);

    void delete(int id, int userId);

    Vote get(int id, int userId);

    void update(Vote vote, int userId, int restaurantId);

    Map<Restaurant, Long> getAll(int restaurantId);

    List<Vote> getAllForRestaurant(int restaurantId);
}
