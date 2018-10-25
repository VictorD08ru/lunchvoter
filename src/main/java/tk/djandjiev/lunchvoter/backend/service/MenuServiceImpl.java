package tk.djandjiev.lunchvoter.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.djandjiev.lunchvoter.backend.model.MenuItem;
import tk.djandjiev.lunchvoter.backend.repository.MenuRepository;
import tk.djandjiev.lunchvoter.backend.repository.RestaurantRepository;
import tk.djandjiev.lunchvoter.backend.to.MenuItemTO;
import tk.djandjiev.lunchvoter.backend.util.MenuUtil;
import tk.djandjiev.lunchvoter.backend.util.exception.IllegalRequestDataException;
import tk.djandjiev.lunchvoter.backend.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static tk.djandjiev.lunchvoter.backend.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @CacheEvict(value = "menu", allEntries = true)
    @Override
    @Transactional
    public MenuItem create(MenuItem menuItem, int restaurantId) {
        Assert.notNull(menuItem, "menuItem must not be null");
        if (!menuItem.isNew() && Objects.isNull(menuItem.getId())) {
            return null;
        }
        menuItem.setRestaurant(restaurantRepository.getOne(restaurantId));
        return menuRepository.save(menuItem);
    }

    @CacheEvict(value = "menu", allEntries = true)
    @Override
    @Transactional
    public void delete(int id, int restaurantId) {
        MenuItem item = get(id);
        if (item.getRestaurant().getId().equals(restaurantId)) {
           menuRepository.delete(id);
        } else {
            throw new IllegalRequestDataException(item + " is not from restaurant " + restaurantId);
        }
    }

    @Override
    public MenuItem get(int id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found entity with id=" + id));
    }

    @CacheEvict(value = "menu", allEntries = true)
    @Override
    @Transactional
    public void update(MenuItemTO menuItemTO, int restaurantId) {
        Assert.notNull(menuItemTO, "menuItem must not be null");
        MenuItem menuItem = MenuUtil.updateFromTO(get(menuItemTO.getId()), menuItemTO);
        checkNotFoundWithId(menuRepository.save(menuItem), menuItem.getId());
    }

    @Cacheable("menu")
    @Override
    public List<MenuItem> getAll(int restaurantId, LocalDate cookingDate) {
        return menuRepository.getAllByDate(restaurantId, cookingDate);
    }
}
