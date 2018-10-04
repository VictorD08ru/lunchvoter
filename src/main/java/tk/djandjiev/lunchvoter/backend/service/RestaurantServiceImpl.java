package tk.djandjiev.lunchvoter.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.djandjiev.lunchvoter.backend.model.Restaurant;
import tk.djandjiev.lunchvoter.backend.repository.RestaurantRepository;
import tk.djandjiev.lunchvoter.backend.to.RestaurantTO;
import tk.djandjiev.lunchvoter.backend.util.RestaurantUtil;
import tk.djandjiev.lunchvoter.backend.util.exception.NotFoundException;

import java.util.List;

import static tk.djandjiev.lunchvoter.backend.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    private RestaurantRepository repository;

    @Override
    public Restaurant create(Restaurant r) {
        Assert.notNull(r, "restaurant must not be null");
        return repository.save(r);
    }

    @Override
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    @Override
    public Restaurant get(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found entity with id=" + id));
    }

    @Transactional
    @Override
    public void update(RestaurantTO restaurantTO) {
        Assert.notNull(restaurantTO, "restaurant must not be null");
        Restaurant r = RestaurantUtil.updateFromTo(get(restaurantTO.getId()), restaurantTO);
        checkNotFoundWithId(repository.save(r), restaurantTO.getId());
    }

    @Override
    public List<Restaurant> getAll() {
        return repository.findAll();
    }
}
