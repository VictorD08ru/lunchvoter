package tk.djandjiev.lunchvoter.backend.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tk.djandjiev.lunchvoter.backend.View;
import tk.djandjiev.lunchvoter.backend.model.Vote;
import tk.djandjiev.lunchvoter.backend.service.VoteService;
import tk.djandjiev.lunchvoter.backend.to.VoteTO;
import tk.djandjiev.lunchvoter.backend.util.SecurityUtil;
import tk.djandjiev.lunchvoter.backend.util.ValidationUtil;
import tk.djandjiev.lunchvoter.backend.util.VoteUtil;
import tk.djandjiev.lunchvoter.backend.util.exception.ApplicationException;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalTime;

@RestController
@RequestMapping(value = VoteController.VOTE_URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VoteController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String VOTE_URL = "/profile/votes";

    @Autowired
    private VoteService voteService;

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete restaurant {}", id);
        voteService.delete(id, userId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody VoteTO voteTO, @PathVariable("id") int id) {
        ValidationUtil.assureIdConsistent(voteTO, id);
        int userId = SecurityUtil.authUserId();
        log.info("update restaurant {}", id);
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            voteService.update(voteTO, userId);
        } else {
            throw new ApplicationException("too late for vote", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Vote> create(@Valid @RequestBody VoteTO voteTO) {
        ValidationUtil.checkNew(voteTO);
        int userId = SecurityUtil.authUserId();
        log.info("create vote for restaurant {}", voteTO.getRestaurantId());
        Vote created = voteService.create(voteTO, userId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/profile/votes/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping
    public VoteTO get() {
        int userId = SecurityUtil.authUserId();
        log.info("get user's {} vote", userId);
        return VoteUtil.getTO(voteService.get(userId));
    }
}
