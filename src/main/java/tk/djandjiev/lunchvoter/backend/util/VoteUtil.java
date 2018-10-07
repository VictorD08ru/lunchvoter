package tk.djandjiev.lunchvoter.backend.util;

import tk.djandjiev.lunchvoter.backend.model.Vote;
import tk.djandjiev.lunchvoter.backend.to.VoteTO;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {
    private VoteUtil() {}

    public static Vote createNewFromTO(VoteTO voteTO) {
        return new Vote(voteTO.getId(), voteTO.getVoteDate(), null, null);
    }

    public static VoteTO getTO(Vote vote) {
        return new VoteTO(vote.getId(), vote.getVoteDate(), vote.getRestaurant().getId());
    }

    public static List<VoteTO> getTOList (Collection<Vote> votes) {
        return votes.stream().filter(v -> LocalDate.now().equals(v.getVoteDate())).map(VoteUtil::getTO).collect(Collectors.toList());
    }
}
