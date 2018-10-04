package tk.djandjiev.lunchvoter.backend.to;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public class VoteTO extends BaseTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate voteDate;

    @NotNull
    private Integer restaurantId;

    public VoteTO() {
    }

    public VoteTO(Integer id, @NotNull LocalDate voteDate, @NotNull Integer restaurantId) {
        super(id);
        this.voteDate = voteDate;
        this.restaurantId = restaurantId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDate getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(LocalDate voteDate) {
        this.voteDate = voteDate;
    }
}
