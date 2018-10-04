package tk.djandjiev.lunchvoter.backend.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import tk.djandjiev.lunchvoter.backend.model.Vote;
import tk.djandjiev.lunchvoter.backend.service.VoteService;
import tk.djandjiev.lunchvoter.backend.to.VoteTO;
import tk.djandjiev.lunchvoter.backend.util.JsonUtil;
import tk.djandjiev.lunchvoter.backend.util.RestaurantTestData;
import tk.djandjiev.lunchvoter.backend.util.TestUtil;
import tk.djandjiev.lunchvoter.backend.util.VoteUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tk.djandjiev.lunchvoter.backend.util.TestUtil.readFromJson;
import static tk.djandjiev.lunchvoter.backend.util.TestUtil.userAuth;
import static tk.djandjiev.lunchvoter.backend.util.UserTestData.*;
import static tk.djandjiev.lunchvoter.backend.util.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = "/profile/votes/";

    @Autowired
    private VoteService voteService;

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + VOTE2_ID)
                .with(userAuth(USER1)))
                .andExpect(status().isNoContent())
                .andDo(print());
        List<Vote> votes = new ArrayList<>(VOTES);
        votes.remove(VOTE2);
        assertMatch(voteService.getAll(RestaurantTestData.RESTAURANT11_ID),
                getVotesForRestaurant(votes, RestaurantTestData.RESTAURANT11_ID));
    }

    //update test works only when it invokes earlier than 11:00 ^_^
    @Test
    void testUpdate() throws Exception {
        Vote updated = new Vote(VOTE2);
        updated.setRestaurant(RestaurantTestData.RESTAURANT13);
        VoteTO updatedTO = VoteUtil.asTo(updated);

        TestUtil.print(mockMvc.perform(put(REST_URL + VOTE2_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updatedTO))
                .with(userAuth(USER1)))
                .andExpect(status().isNoContent()));

        assertMatch(voteService.get(VOTE2_ID, USER1_ID), updated);
    }

    @Test
    void testCreate() throws Exception {
        VoteTO voteTO = new VoteTO(null, LocalDate.now(), RestaurantTestData.RESTAURANT13_ID);
        Vote vote = getCreated();
        ResultActions action = TestUtil.print(mockMvc.perform(post("/profile/votes")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(voteTO))
                .with(userAuth(USER0)))
                .andExpect(status().isCreated()));

        Vote returned = readFromJson(action, Vote.class);
        vote.setId(returned.getId());
        assertMatch(returned, vote);
        List<Vote> votes = new ArrayList<>(VOTES);
        votes.add(vote);
        assertMatch(voteService.getAll(RestaurantTestData.RESTAURANT13_ID),
                getVotesForRestaurant(votes, RestaurantTestData.RESTAURANT13_ID));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + VOTE1_ID)
                .with(userAuth(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(TestUtil.contentJson(VoteUtil.asTo(VOTE1)));
    }

    @Test
    void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL + VOTE1_ID))
                .andExpect(status().isUnauthorized());
    }
}