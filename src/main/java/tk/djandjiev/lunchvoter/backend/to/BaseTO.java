package tk.djandjiev.lunchvoter.backend.to;

import tk.djandjiev.lunchvoter.backend.HasId;

public abstract class BaseTO implements HasId {
    protected Integer id;

    public BaseTO() {
    }

    public BaseTO(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
