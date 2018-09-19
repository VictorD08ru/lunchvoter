package tk.djandjiev.lunchvoter.backend.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

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
    private String dish;

    @Column(name = "price", nullable = false)
    @Range(min = 1, max = 2000_000)
    private int price;

    @Column(name = "enabled", nullable = false, columnDefinition = "boolean default true")
    private boolean enabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    public Menu() {
    }

    public Menu(Integer id, String dish, int price, boolean enabled, Restaurant restaurant) {
        super(id);
        this.dish = dish;
        this.price = price;
        this.enabled = enabled;
        this.restaurant = restaurant;
    }

    public Menu(Integer id, String dish, int price, Restaurant restaurant) {
        this(id, dish, price, true, restaurant);
    }

    public Menu(Menu menu) {
        this(menu.getId(), menu.getDish(), menu.getPrice(), menu.isEnabled(), menu.getRestaurant());
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
                "dish='" + dish + '\'' +
                ", price=" + price +
                '}';
    }
}
