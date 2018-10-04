package tk.djandjiev.lunchvoter.backend.to;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class MenuTO extends BaseTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    @SafeHtml
    private String dish;

    @NotNull
    @Range(min = 5, max = 2_000_000)
    private Integer price;

    public MenuTO() {
    }

    public MenuTO(Integer id, @NotBlank @Size(min = 2, max = 100) @SafeHtml String dish, @NotNull @Range(min = 5, max = 2_000_000) Integer price) {
        super(id);
        this.dish = dish;
        this.price = price;
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
}
