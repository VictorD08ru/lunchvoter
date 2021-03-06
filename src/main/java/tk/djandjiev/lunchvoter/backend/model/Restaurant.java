package tk.djandjiev.lunchvoter.backend.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import tk.djandjiev.lunchvoter.backend.View;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "restaurants", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")
})
public class Restaurant extends AbstractNamedEntity {

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @BatchSize(size = 50)
    @NotNull(groups = View.Persist.class)
    private List<MenuItem> menu;

    @OneToMany(mappedBy = "restaurant")
    @NotNull(groups = View.Persist.class)
    private Collection<Vote> votes;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
        if (menu == null) {
            this.menu = Collections.emptyList();
        }
        if (votes == null) {
            this.votes = Collections.emptyList();
        }
    }

    public Restaurant(Integer id, String name, List<MenuItem> menu, Collection<Vote> votes) {
        super(id, name);
        this.menu = menu;
        this.votes = votes;
    }

    public Restaurant(Restaurant r) {
        this(r.getId(), r.getName(), r.getMenu(), r.getVotes());
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
    }

    public Collection<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Collection<Vote> votes) {
        this.votes = votes;
    }
}
