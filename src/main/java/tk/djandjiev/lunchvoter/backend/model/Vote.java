package tk.djandjiev.lunchvoter.backend.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import tk.djandjiev.lunchvoter.backend.View;

import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "votes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"vote_date", "user_id"}, name = "votes_unique_user_date_idx")
})
public class Vote extends AbstractBaseEntity {

    @Column(name = "vote_date", columnDefinition = "date default current_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate voteDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public Vote() {
        this.voteDate = LocalDate.now();
    }

    public Vote(Integer id, LocalDate voteDate, User user, Restaurant restaurant) {
        super(id);
        this.voteDate = voteDate;
        this.user = user;
        this.restaurant = restaurant;
    }

    public Vote(Integer id, User user, Restaurant restaurant) {
        this(id, LocalDate.now(), user, restaurant);
    }

    public Vote(Restaurant restaurant) {
        this(null, LocalDate.now(), null, restaurant);
    }

    public Vote(Vote vote) {
        this(vote.getId(), vote.getVoteDate(), vote.getUser(), vote.getRestaurant());
    }

    public LocalDate getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(LocalDate voteDate) {
        this.voteDate = voteDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                "voteDate=" + voteDate +
                ", restaurant=" + getRestaurant().getId() +
                '}';
    }
}
