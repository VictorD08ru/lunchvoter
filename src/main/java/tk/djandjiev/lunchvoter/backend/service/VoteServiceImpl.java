package tk.djandjiev.lunchvoter.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.djandjiev.lunchvoter.backend.model.Restaurant;
import tk.djandjiev.lunchvoter.backend.model.User;
import tk.djandjiev.lunchvoter.backend.model.Vote;
import tk.djandjiev.lunchvoter.backend.repository.RestaurantRepository;
import tk.djandjiev.lunchvoter.backend.repository.UserRepository;
import tk.djandjiev.lunchvoter.backend.repository.VoteRepository;
import tk.djandjiev.lunchvoter.backend.to.VoteTO;
import tk.djandjiev.lunchvoter.backend.util.VoteUtil;
import tk.djandjiev.lunchvoter.backend.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

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
    public Vote create(VoteTO voteTO, int userId) {
        Assert.notNull(voteTO,"vote must not be null");
        Vote vote = VoteUtil.createNewFromTO(voteTO);
        vote.setUser(userRepository.getOne(userId));
        vote.setRestaurant(restaurantRepository.getOne(voteTO.getRestaurantId()));
        return voteRepository.save(vote);
    }

    @Override
    public void delete(int id, int userId) {
        checkNotFoundWithId(voteRepository.delete(id, userId) != 0, id);
    }

    @Override
    public Vote getForDate(int userId, LocalDate date) {
        return voteRepository.getForDate(userId, date)
                .orElseThrow(() -> new NotFoundException(Integer.toString(userId)));
    }

    @Override
    @Transactional
    public void update(VoteTO voteTO, int userId) {
        Assert.notNull(voteTO,"voteTO must not be null");
        User user = userRepository.getOne(userId);
        checkNotFoundWithId(voteTO, voteTO.getId());
        Restaurant r = restaurantRepository.getOne(voteTO.getRestaurantId());
        Vote vote = new Vote(voteTO.getId(), voteTO.getVoteDate(), user, r);
        voteRepository.save(vote);
    }

    @Override
    public List<Vote> getAll(int restaurantId) {
        return voteRepository.getAllForRestaurant(restaurantId);
    }
}
