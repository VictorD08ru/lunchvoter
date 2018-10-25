package tk.djandjiev.lunchvoter.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import tk.djandjiev.lunchvoter.backend.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    Vote save(Vote vote);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.voteDate=:voteDate")
    Optional<Vote> getForDate(@Param("userId") Integer userId, @Param("voteDate") LocalDate voteDate);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId AND v.voteDate=CURRENT_DATE")
    List<Vote> getAllForRestaurant(@Param("restaurantId") Integer restaurantId);
}
