package tk.djandjiev.lunchvoter.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.djandjiev.lunchvoter.backend.model.Restaurant;
import tk.djandjiev.lunchvoter.backend.model.Vote;
import tk.djandjiev.lunchvoter.backend.repository.RestaurantRepository;
import tk.djandjiev.lunchvoter.backend.repository.UserRepository;
import tk.djandjiev.lunchvoter.backend.repository.VoteRepository;
import tk.djandjiev.lunchvoter.backend.util.exception.NotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static tk.djandjiev.lunchvoter.backend.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    @Transactional
    public Vote create(Vote vote, int userId, int restaurantId) {
        Assert.notNull(vote,"vote must not be null");
        vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        vote.setUser(userRepository.getOne(userId));
        return voteRepository.save(vote);
    }

    @Override
    public void delete(int id, int userId) {
        checkNotFoundWithId(voteRepository.delete(id, userId), id);
    }

    @Override
    public Vote get(int id, int userId) {
        return voteRepository.get(id, userId)
                .orElseThrow(() -> new NotFoundException("Not found entity with id=" + id));
    }

    @Override
    @Transactional
    public void update(Vote vote, int userId, int restaurantId) {
        Assert.notNull(vote,"vote must not be null");
        vote.setRestaurant(restaurantRepository.getOne(restaurantId));
        vote.setUser(userRepository.getOne(userId));
        checkNotFoundWithId(voteRepository.save(vote), vote.getId());
    }

    @Override
    public Map<Restaurant, Long> getAll(int restaurantId) {
        return voteRepository.getAll().stream()
                .collect(Collectors.groupingByConcurrent(Vote::getRestaurant, Collectors.counting()));
    }

    @Override
    public List<Vote> getAllForRestaurant(int restaurantId) {
        return voteRepository.getAllForRestaurant(restaurantId);
    }
}
