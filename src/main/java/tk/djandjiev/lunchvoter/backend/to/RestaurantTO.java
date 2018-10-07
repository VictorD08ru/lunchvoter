package tk.djandjiev.lunchvoter.backend.to;

import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class RestaurantTO extends BaseTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    @SafeHtml
    private String name;

    public RestaurantTO() {
    }

    public RestaurantTO(Integer id, @NotBlank @Size(min = 2, max = 100) @SafeHtml String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
