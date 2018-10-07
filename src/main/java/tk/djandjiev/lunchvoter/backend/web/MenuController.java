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
import tk.djandjiev.lunchvoter.backend.model.Menu;
import tk.djandjiev.lunchvoter.backend.service.MenuService;
import tk.djandjiev.lunchvoter.backend.to.MenuTO;
import tk.djandjiev.lunchvoter.backend.util.ValidationUtil;

import java.net.URI;
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
    @DeleteMapping("{rId}/menu/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("rId") int rId, @PathVariable("id") int id) {
        log.info("delete menu point {} for restaurant {}", id, rId);
        menuService.delete(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{rId}/menu/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody MenuTO menuTO,
                               @PathVariable("rId") int rId,
                               @PathVariable("id") int id) {
        ValidationUtil.assureIdConsistent(menuTO, id);
        log.info("update menu point {} for restaurant {}", id, rId);
        menuService.update(menuTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/{rId}/menu", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Menu> create(@Validated(View.Web.class) @RequestBody Menu menu,
                                               @PathVariable("rId") int rId) {
        ValidationUtil.checkNew(menu);
        log.info("create menu point for restaurant {}", rId);
        Menu created = menuService.create(menu, rId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RestaurantController.RESTAURANT_URL + "/{rId}/menu/{id}")
                .buildAndExpand(rId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("{rId}/menu/{id}")
    public MenuTO get(@PathVariable("rId") int rId, @PathVariable("id") int id) {
        log.info("get menu point {} for restaurant {}", id, rId);
        return getTO(menuService.get(id));
    }

    @GetMapping("{rId}/menu")
    public List<MenuTO> getAll(@PathVariable("rId") int rId) {
        log.info("get menu for restaurant {}", rId);
        return getTOList(menuService.getAll(rId));
    }
}
