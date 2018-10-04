package tk.djandjiev.lunchvoter.backend.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import tk.djandjiev.lunchvoter.backend.View;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "menu", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"dish", "restaurant_id"}, name = "menu_unique_dish_per_restrnt_idx")
})
public class Menu extends AbstractBaseEntity {

    @Column(name = "dish", nullable = false, length = 100)
    @NotBlank
    @SafeHtml(groups = {View.Web.class})
    private String dish;

    @Column(name = "price", nullable = false)
    @Range(min = 1, max = 2000_000)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
    private Restaurant restaurant;

    public Menu() {
    }

    public Menu(Integer id, String dish, int price, Restaurant restaurant) {
        super(id);
        this.dish = dish;
        this.price = price;
        this.restaurant = restaurant;
    }

    public Menu(String dish, int price) {
        this(null, dish, price, null);
    }

    public Menu(Menu menu) {
        this(menu.getId(), menu.getDish(), menu.getPrice(), menu.getRestaurant());
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

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                "dish='" + dish + '\'' +
                ", price=" + price +
                '}';
    }
}
