package tk.djandjiev.lunchvoter.backend.service;

import tk.djandjiev.lunchvoter.backend.model.Vote;
import tk.djandjiev.lunchvoter.backend.to.VoteTO;

import java.time.LocalDate;
import java.util.List;

public interface VoteService {
    Vote create(VoteTO voteTO, int userId);

    void delete(int id, int userId);

    Vote getForDate(int userId, LocalDate date);

    void update(VoteTO voteTO, int userId);

    List<Vote> getAll(int restaurantId);
}
