package api_fintech.Users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Gestión de usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Crear un usuario", description = "Crea un usuario para luego poder crear una cuenta bancaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario creadao exitosamente"),
            @ApiResponse(responseCode = "400", description = "JSON inválido o error de validación en los datos de entrada"),
    })
    @PostMapping("/add")
    public User createUser(@RequestBody User user) {
        return userService.AddUser(user);
    }
}
