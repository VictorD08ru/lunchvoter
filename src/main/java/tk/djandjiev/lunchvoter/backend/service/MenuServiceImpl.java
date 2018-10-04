package tk.djandjiev.lunchvoter.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import tk.djandjiev.lunchvoter.backend.model.Menu;
import tk.djandjiev.lunchvoter.backend.repository.MenuRepository;
import tk.djandjiev.lunchvoter.backend.repository.RestaurantRepository;
import tk.djandjiev.lunchvoter.backend.to.MenuTO;
import tk.djandjiev.lunchvoter.backend.util.MenuUtil;
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

    @CacheEvict(value = "menu", allEntries = true)
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

    @CacheEvict(value = "menu", allEntries = true)
    @Override
    public void delete(int id) {
        checkNotFoundWithId(menuRepository.delete(id) != 0, id);
    }

    @Override
    public Menu get(int id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found entity with id=" + id));
    }

    @CacheEvict(value = "menu", allEntries = true)
    @Override
    @Transactional
    public void update(MenuTO menuTO) {
        Assert.notNull(menuTO, "menu must not be null");
        Menu menu = MenuUtil.updateFromTo(get(menuTO.getId()), menuTO);
        checkNotFoundWithId(menuRepository.save(menu), menu.getId());
    }

    @Override
    public List<Menu> getAll(int restaurantId) {
        return menuRepository.findAllByRestaurantId(restaurantId);
    }
}
