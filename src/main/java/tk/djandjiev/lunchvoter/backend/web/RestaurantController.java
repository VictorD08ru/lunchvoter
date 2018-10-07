package tk.djandjiev.lunchvoter.backend.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tk.djandjiev.lunchvoter.backend.model.Restaurant;
import tk.djandjiev.lunchvoter.backend.service.RestaurantService;
import tk.djandjiev.lunchvoter.backend.service.VoteService;
import tk.djandjiev.lunchvoter.backend.to.RestaurantTO;
import tk.djandjiev.lunchvoter.backend.to.VoteTO;
import tk.djandjiev.lunchvoter.backend.util.ValidationUtil;
import tk.djandjiev.lunchvoter.backend.util.VoteUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static tk.djandjiev.lunchvoter.backend.util.RestaurantUtil.*;

@RestController
@RequestMapping(value = RestaurantController.RESTAURANT_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String RESTAURANT_URL = "/restaurants";

    @Autowired
    private RestaurantService rService;

    @Autowired
    private VoteService voteService;

    @GetMapping("/{id}")
    public RestaurantTO get(@PathVariable("id") int id) {
        log.info("get restaurant {}", id);
        return getTO(rService.get(id));
    }

    @GetMapping
    public List<RestaurantTO> getAll() {
        log.info("getAll restaurants");
        return getTOList(rService.getAll());
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
    public void update(@Valid @RequestBody RestaurantTO restaurantTO, @PathVariable("id") int id) {
        log.info("update restaurant {}", id);
        ValidationUtil.assureIdConsistent(restaurantTO, id);
        rService.update(restaurantTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody RestaurantTO r) {
        ValidationUtil.checkNew(r);
        log.info("create {}", r);
        Restaurant created = rService.create(createNewFromTO(r));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RestaurantController.RESTAURANT_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{id}/votes")
    public List<VoteTO> getVotes(@PathVariable("id") int id) {
        return VoteUtil.getTOList(voteService.getAll(id));
    }
}
