package tk.djandjiev.lunchvoter.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import tk.djandjiev.lunchvoter.backend.model.Restaurant;
import tk.djandjiev.lunchvoter.backend.repository.RestaurantRepository;
import tk.djandjiev.lunchvoter.backend.util.exception.NotFoundException;

import java.util.List;

import static tk.djandjiev.lunchvoter.backend.util.ValidationUtil.checkNotFoundWithId;

@Service
public class ReastaurantServiceImpl implements ReastaurantService {
    @Autowired
    private RestaurantRepository repository;

    @Override
    public Restaurant create(Restaurant r) {
        Assert.notNull(r, "restaurant must not be null");
        return repository.save(r);
    }

    @Override
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Restaurant get(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found entity with id=" + id));
    }

    @Override
    public void update(Restaurant r) {
        Assert.notNull(r, "restaurant must not be null");
        checkNotFoundWithId(repository.save(r), r.getId());
    }

    @Override
    public List<Restaurant> getAll() {
        return repository.findAll();
    }
}
