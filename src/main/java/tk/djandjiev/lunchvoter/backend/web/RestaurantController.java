package tk.djandjiev.lunchvoter.backend.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tk.djandjiev.lunchvoter.backend.View;
import tk.djandjiev.lunchvoter.backend.model.Menu;
import tk.djandjiev.lunchvoter.backend.model.Restaurant;
import tk.djandjiev.lunchvoter.backend.service.MenuService;
import tk.djandjiev.lunchvoter.backend.service.RestaurantService;
import tk.djandjiev.lunchvoter.backend.service.VoteService;
import tk.djandjiev.lunchvoter.backend.to.MenuTO;
import tk.djandjiev.lunchvoter.backend.to.RestaurantTO;
import tk.djandjiev.lunchvoter.backend.util.MenuUtil;
import tk.djandjiev.lunchvoter.backend.util.RestaurantUtil;
import tk.djandjiev.lunchvoter.backend.util.ValidationUtil;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService rService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private VoteService voteService;

    @GetMapping("/{id}")
    public RestaurantTO get(@PathVariable("id") int id) {
        log.info("get restaurant {}", id);
        Restaurant r = rService.get(id);
        return RestaurantUtil.asTOWithVotes(r, menuService.getAll(id), voteService.getAll(r.getId()));
    }

    @GetMapping
    public List<RestaurantTO> getAll() {
        log.info("getAll restaurants");
        return rService.getAll()
                .stream()
                .map(r -> RestaurantUtil.asTOWithVotes(r, menuService.getAll(r.getId()),voteService.getAll(r.getId()))
                )
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete restaurant {}", id);
        rService.delete(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody RestaurantTO restaurantTO, @PathVariable("id") int id) {
        log.info("update restaurant {}", id);
        ValidationUtil.assureIdConsistent(restaurantTO, id);
        rService.update(restaurantTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Restaurant> create(@Validated(View.Web.class) @RequestBody Restaurant r) {
        ValidationUtil.checkNew(r);
        log.info("create {}", r);
        Restaurant created = rService.create(r);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/restaurants/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{rId}/menu/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMenuItem(@PathVariable("rId") int rId, @PathVariable("id") int id) {
        log.info("delete menu point {} for restaurant {}", id, rId);
        menuService.delete(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{rId}/menu/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateMenuItem(@Validated(View.Web.class) @RequestBody MenuTO menuTO,
                               @PathVariable("rId") int rId,
                               @PathVariable("id") int id) {
        ValidationUtil.assureIdConsistent(menuTO, id);
        log.info("update menu point {} for restaurant {}", id, rId);
        menuService.update(menuTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/{rId}/menu", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Menu> createMenuItem(@Validated(View.Web.class) @RequestBody Menu menu,
                                               @PathVariable("rId") int rId) {
        ValidationUtil.checkNew(menu);
        log.info("create menu point for restaurant {}", rId);
        Menu created = menuService.create(menu, rId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/restaurants/{rId}/menu/{id}")
                .buildAndExpand(rId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{rId}/menu/{id}")
    public MenuTO getMenuItem(@PathVariable("rId") int rId, @PathVariable("id") int id) {
        log.info("get menu point {} for restaurant {}", id, rId);
        return MenuUtil.asTo(menuService.get(id));
    }

    @GetMapping("/{rId}/menu")
    public List<MenuTO> getFullMenu(@PathVariable("rId") int rId) {
        return MenuUtil.getTOList(menuService.getAll(rId));
    }

}
