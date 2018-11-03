package tk.djandjiev.lunchvoter.backend.web;

import org.junit.jupiter.api.Assumptions;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tk.djandjiev.lunchvoter.backend.util.TestUtil.readFromJson;
import static tk.djandjiev.lunchvoter.backend.util.TestUtil.userAuth;
import static tk.djandjiev.lunchvoter.backend.util.TestUtil.userHttpBasic;
import static tk.djandjiev.lunchvoter.backend.util.UserTestData.*;
import static tk.djandjiev.lunchvoter.backend.util.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = "/profile/votes/";
    private static final String IGNORE = "test is ignored after 11:00 a.m.";

    @Autowired
    private VoteService voteService;

    @Test
    void testDelete() throws Exception {
        Assumptions.assumeTrue(LocalTime.now().isBefore(LocalTime.of(11,0)), IGNORE);
        mockMvc.perform(delete(REST_URL + VOTE2_ID)
                .with(userAuth(USER1)))
                .andExpect(status().isNoContent())
                .andDo(print());
        List<Vote> votes = new ArrayList<>(VOTES);
        votes.remove(VOTE2);
        assertMatch(voteService.getAll(RestaurantTestData.RESTAURANT11_ID),
                getVotesForRestaurant(votes, RestaurantTestData.RESTAURANT11_ID));
    }

    @Test
    void testUpdate() throws Exception {
        Assumptions.assumeTrue(LocalTime.now().isBefore(LocalTime.of(11,0)), IGNORE);
        Vote updated = new Vote(VOTE2);
        updated.setRestaurant(RestaurantTestData.RESTAURANT13);
        VoteTO updatedTO = VoteUtil.getTO(updated);

        TestUtil.print(mockMvc.perform(put(REST_URL+ updated.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonUtil.writeValue(updatedTO))
                .with(userAuth(USER1)))
                .andExpect(status().isNoContent()));

        assertMatch(voteService.getForDate(USER1_ID, LocalDate.now()), updated);
    }

    @Test
    void testCreate() throws Exception {
        Assumptions.assumeTrue(LocalTime.now().isBefore(LocalTime.of(11,0)), IGNORE);
        VoteTO voteTO = new VoteTO(null, LocalDate.now(), RestaurantTestData.RESTAURANT13_ID);
        Vote vote = getCreated();
        ResultActions action = TestUtil.print(mockMvc.perform(post(REST_URL)
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
        mockMvc.perform(get(REST_URL)
                .with(userAuth(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(TestUtil.contentJson(VoteUtil.getTO(VOTE1)));
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER0)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testDeleteNotFound() throws Exception {
        Assumptions.assumeTrue(LocalTime.now().isBefore(LocalTime.of(11,0)), IGNORE);
        mockMvc.perform(delete(REST_URL + 1)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL + VOTE1_ID))
                .andExpect(status().isUnauthorized());
    }
}