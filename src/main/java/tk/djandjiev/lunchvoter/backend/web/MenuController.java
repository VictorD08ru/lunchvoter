package tk.djandjiev.lunchvoter.backend.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tk.djandjiev.lunchvoter.backend.View;
import tk.djandjiev.lunchvoter.backend.model.MenuItem;
import tk.djandjiev.lunchvoter.backend.service.MenuService;
import tk.djandjiev.lunchvoter.backend.to.MenuItemTO;
import tk.djandjiev.lunchvoter.backend.util.ValidationUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static tk.djandjiev.lunchvoter.backend.util.MenuUtil.*;

@RestController
@RequestMapping(value = RestaurantController.RESTAURANT_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MenuController {
    private static final Logger log = getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{restaurantId}/menu/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") int restaurantId, @PathVariable("id") int id) {
        log.info("delete menuItem point {} for restaurant {}", id, restaurantId);
        menuService.delete(id, restaurantId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{restaurantId}/menu/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody MenuItemTO menuItemTO,
                               @PathVariable("restaurantId") int restaurantId,
                               @PathVariable("id") int id) {
        ValidationUtil.assureIdConsistent(menuItemTO, id);
        log.info("update menuItem point {} for restaurant {}", id, restaurantId);
        menuService.update(menuItemTO, restaurantId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/{restaurantId}/menu", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<MenuItem> create(@Validated(View.Web.class) @RequestBody MenuItem menuItem,
                                           @PathVariable("restaurantId") int restaurantId) {
        ValidationUtil.checkNew(menuItem);
        log.info("create menuItem point for restaurant {}", restaurantId);
        MenuItem created = menuService.create(menuItem, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RestaurantController.RESTAURANT_URL + "/{restaurantId}/menuItem/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("{restaurantId}/menu/{id}")
    public MenuItemTO get(@PathVariable("restaurantId") int restaurantId, @PathVariable("id") int id) {
        log.info("get menuItem point {} for restaurant {}", id, restaurantId);
        return getTO(menuService.get(id));
    }

    @GetMapping("{restaurantId}/menu")
    public List<MenuItemTO> getAll(@PathVariable("restaurantId") int restaurantId) {
        log.info("get menuItem for restaurant {}", restaurantId);
        return getTOList(menuService.getAll(restaurantId, LocalDate.now()));
    }
}
