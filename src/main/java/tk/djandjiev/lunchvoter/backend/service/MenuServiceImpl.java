package tk.djandjiev.lunchvoter.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.djandjiev.lunchvoter.backend.model.Menu;
import tk.djandjiev.lunchvoter.backend.repository.MenuRepository;
import tk.djandjiev.lunchvoter.backend.repository.RestaurantRepository;
import tk.djandjiev.lunchvoter.backend.util.exception.NotFoundException;

import java.util.List;
import java.util.Objects;

import static tk.djandjiev.lunchvoter.backend.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    @Transactional
    public Menu create(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        if (!menu.isNew() && Objects.isNull(menu.getId())) {
            return null;
        }
        menu.setRestaurant(restaurantRepository.getOne(restaurantId));
        return menuRepository.save(menu);
    }

    @Override
    public void delete(int id) {
        checkNotFoundWithId(menuRepository.delete(id), id);
    }

    @Override
    public Menu get(int id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found entity with id=" + id));
    }

    @Override
    @Transactional
    public void update(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        menu.setRestaurant(restaurantRepository.getOne(restaurantId));
        checkNotFoundWithId(menuRepository.save(menu), menu.getId());
    }

    @Override
    public List<Menu> getAll(int restaurantId) {
        return menuRepository.findAllByRestaurantId(restaurantId);
    }

    @Override
    public List<Menu> getAllForCurrentDay(int restaurantId) {
        return menuRepository.findAllByRestaurantIdAndEnabledIsTrue(restaurantId);
    }
}
