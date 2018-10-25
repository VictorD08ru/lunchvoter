package tk.djandjiev.lunchvoter.backend.to;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

public class MenuItemTO extends BaseTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    @SafeHtml
    private String dish;

    @NotNull
    @Range(min = 5, max = 2_000_000)
    private Integer price;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate cookingDate;

    public MenuItemTO() {
    }

    public MenuItemTO(Integer id,
                      @NotBlank @Size(min = 2, max = 100) @SafeHtml String dish,
                      @NotNull @Range(min = 5, max = 2_000_000) Integer price,
                      @NotNull LocalDate cookingDate) {
        super(id);
        this.dish = dish;
        this.price = price;
        this.cookingDate = cookingDate;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDate getCookingDate() {
        return cookingDate;
    }

    public void setCookingDate(LocalDate cookingDate) {
        this.cookingDate = cookingDate;
    }
}
