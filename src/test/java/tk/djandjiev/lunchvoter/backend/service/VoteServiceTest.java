package tk.djandjiev.lunchvoter.backend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import tk.djandjiev.lunchvoter.backend.to.VoteTO;
import tk.djandjiev.lunchvoter.backend.model.Vote;
import tk.djandjiev.lunchvoter.backend.util.RestaurantTestData;
import tk.djandjiev.lunchvoter.backend.util.VoteUtil;
import tk.djandjiev.lunchvoter.backend.util.exception.ApplicationException;
import tk.djandjiev.lunchvoter.backend.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static tk.djandjiev.lunchvoter.backend.util.UserTestData.*;
import static tk.djandjiev.lunchvoter.backend.util.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService voteService;

    @Test
    void create() throws Exception {
        VoteTO voteTO = new VoteTO(null, LocalDate.now(), RestaurantTestData.RESTAURANT11_ID);
        Vote created = voteService.create(voteTO, USER0_ID);
        Vote newVote = new Vote();
        newVote.setId(created.getId());
        newVote.setUser(USER0);
        newVote.setRestaurant(RestaurantTestData.RESTAURANT11);
        List<Vote> list = new ArrayList<>(VOTES);
        list.add(newVote);
        assertMatch(voteService.getAll(RestaurantTestData.RESTAURANT11_ID),
                getVotesForRestaurant(list, RestaurantTestData.RESTAURANT11_ID));
    }

    @Test
    void createDuplicate() throws Exception {
        Assertions.assertThrows(DataAccessException.class, () -> voteService.create(new VoteTO(null, LocalDate.now(), RestaurantTestData.RESTAURANT11_ID), ADMIN_ID));
    }

    @Test
    void delete() throws Exception {
        voteService.delete(VOTE1_ID, ADMIN_ID);
        List<Vote> list = new ArrayList<>(VOTES);
        list.remove(VOTE1);
        assertMatch(voteService.getAll(RestaurantTestData.RESTAURANT15_ID),
                getVotesForRestaurant(list, RestaurantTestData.RESTAURANT15_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        Assertions.assertThrows(ApplicationException.class, () -> voteService.delete(1, USER0_ID));
    }

    @Test
    void deleteNotActualUser() throws Exception {
        Assertions.assertThrows(ApplicationException.class, () -> voteService.delete(VOTE1_ID, USER0_ID));
    }

    @Test
    void get() throws Exception {
        assertMatch(voteService.getForDate(ADMIN_ID, LocalDate.now()), VOTE1);
    }

    @Test
    void getNotFound() throws Exception {
        Assertions.assertThrows(NotFoundException.class, () -> voteService.getForDate(USER0_ID, LocalDate.now()));
    }

    @Test
    void update() throws Exception {
        assertMatch(voteService.getAll(RestaurantTestData.RESTAURANT15_ID),
                getVotesForRestaurant(VOTES, RestaurantTestData.RESTAURANT15_ID));
        VoteTO updated = VoteUtil.getTO(VOTE1);
        updated.setRestaurantId(RestaurantTestData.RESTAURANT13_ID);
        voteService.update(updated, ADMIN_ID);
        List<Vote> votes = new ArrayList<>(VOTES);
        Vote vote = VoteUtil.createNewFromTO(updated);
        vote.setRestaurant(RestaurantTestData.RESTAURANT13);
        votes.set(0, vote);
        assertMatch(voteService.getAll(RestaurantTestData.RESTAURANT13_ID), VOTE10, vote);
    }

    @Test
    void getAllForRestaurant() throws Exception {
        assertMatch(voteService.getAll(RestaurantTestData.RESTAURANT14_ID), VOTE7, VOTE8, VOTE9);
    }
}