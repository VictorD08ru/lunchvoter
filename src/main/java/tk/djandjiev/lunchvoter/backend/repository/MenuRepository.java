package tk.djandjiev.lunchvoter.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import tk.djandjiev.lunchvoter.backend.model.MenuItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<MenuItem, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM MenuItem m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    MenuItem save(MenuItem menuItem);

    @Override
    Optional<MenuItem> findById(Integer id);

    @Query("SELECT m FROM MenuItem m WHERE m.restaurant.id=:restaurantId AND m.cookingDate=:cookingDate")
    List<MenuItem> getAllByDate(@Param("restaurantId") Integer restaurantId, @Param("cookingDate") LocalDate cookingDate);
}