package tk.djandjiev.lunchvoter.backend.model;


import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;
import tk.djandjiev.lunchvoter.backend.View;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "menu", uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"dish", "cooking_date", "restaurant_id"},
                name = "menu_unique_dish_of_cooking_date_per_restrnt_idx")
})
public class MenuItem extends AbstractBaseEntity {

    @Column(name = "dish", nullable = false, length = 100)
    @NotBlank
    @SafeHtml(groups = {View.Web.class})
    private String dish;

    @Column(name = "price", nullable = false)
    @Range(min = 1, max = 2000_000)
    private int price;

    @NotNull
    @Column(name = "cooking_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate cookingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
    private Restaurant restaurant;


    public MenuItem() {
    }

    public MenuItem(Integer id, String dish, int price, LocalDate cookingDate, Restaurant restaurant) {
        super(id);
        this.dish = dish;
        this.price = price;
        this.cookingDate = cookingDate;
        this.restaurant = restaurant;
    }

    public MenuItem(String dish, int price, LocalDate cookingDate) {
        this(null, dish, price, cookingDate, null);
    }

    public MenuItem(MenuItem menuItem) {
        this(menuItem.getId(), menuItem.getDish(), menuItem.getPrice(), menuItem.getCookingDate(), menuItem.getRestaurant());
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getCookingDate() {
        return cookingDate;
    }

    public void setCookingDate(LocalDate cookingDate) {
        this.cookingDate = cookingDate;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                "dish='" + dish + '\'' +
                ", price=" + price +
                '}';
    }
}
