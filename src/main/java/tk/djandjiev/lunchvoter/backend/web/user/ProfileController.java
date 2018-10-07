package tk.djandjiev.lunchvoter.backend.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tk.djandjiev.lunchvoter.backend.model.User;
import tk.djandjiev.lunchvoter.backend.to.AuthorizedUser;
import tk.djandjiev.lunchvoter.backend.to.UserTO;
import tk.djandjiev.lunchvoter.backend.util.UserUtil;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(ProfileController.REST_URL)
public class ProfileController extends AbstractUserController {
    static final String REST_URL = "/profile";

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User get(@AuthenticationPrincipal AuthorizedUser authUser) {
        return super.get(authUser.getId());
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authUser) {
        super.delete(authUser.getId());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTO userTo) {
        User created = super.create(UserUtil.createNewFromTO(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserTO userTo, @AuthenticationPrincipal AuthorizedUser authUser) {
        super.update(userTo, authUser.getId());
    }
}