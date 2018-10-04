package tk.djandjiev.lunchvoter.backend.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tk.djandjiev.lunchvoter.backend.View;
import tk.djandjiev.lunchvoter.backend.model.User;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(AdminController.REST_URL)
public class AdminController extends AbstractUserController {
    static final String REST_URL = "/admin/users";

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> createWithLocation(@Validated(View.Web.class) @RequestBody User user) {
        User created = super.create(user);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody User user, @PathVariable("id") int id) {
        super.update(user, id);
    }

    @Override
    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User getByMail(@RequestParam("email") String email) {
        return super.getByMail(email);
    }
}