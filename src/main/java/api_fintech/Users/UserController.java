package api_fintech.Users;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Crear un usuario")
    @PostMapping("/add/user")
    public User createUser(@RequestBody User user) {
        return userService.AddUser(user);
    }
}
