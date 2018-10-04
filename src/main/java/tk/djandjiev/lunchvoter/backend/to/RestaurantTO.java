package tk.djandjiev.lunchvoter.backend.to;

import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class RestaurantTO extends BaseTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    @SafeHtml
    private String name;

    @NotNull
    private List<MenuTO> menu;

    @NotNull
    private List<VoteTO> votes;

    public RestaurantTO() {
    }

    public RestaurantTO(Integer id, @NotBlank @Size(min = 2, max = 100) @SafeHtml String name, @NotNull List<MenuTO> menu, @NotNull List<VoteTO> votes) {
        super(id);
        this.name = name;
        this.menu = menu;
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuTO> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuTO> menu) {
        this.menu = menu;
    }

    public List<VoteTO> getVotes() {
        return votes;
    }

    public void setVotes(List<VoteTO> votes) {
        this.votes = votes;
    }
}
