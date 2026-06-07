package api_fintech.Accounts;

import api_fintech.Accounts.Dtos.AccountResponseDTO;
import api_fintech.Accounts.Dtos.RequestAccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Accounts", description = "Gestión de cuentas bancarias")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(
            summary = "Obtener todas las cuentas",
            description = "Retorna una lista con todas las cuentas bancarias registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cuentas obtenida exitosamente")
    })
    @GetMapping("/list")
    public List<Account> getAccounts() {
        return accountService.getAllAccounts();
    }

    @Operation(
            summary = "Obtener una cuenta por ID",
            description = "Busca y retorna los detalles de una cuenta específica utilizando su ID único de base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "La cuenta con el ID provisto no existe")
    })
    @GetMapping("/id")
    public AccountResponseDTO getAccountById(@RequestParam Long id) {
        return accountService.getAccount(id);
    }

    @Operation(
            summary = "Crear una cuenta",
            description = "Crea una cuenta asociada a un usuario existente. El saldo inicial no puede ser negativo.")
    @PostMapping("/add")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "JSON inválido o error de validación en los datos de entrada"),
            @ApiResponse(responseCode = "404", description = "El ID del usuario provisto no existe")
    })
    public Account createAccount(@RequestBody RequestAccountDTO account) {
        return accountService.createAccount(account);
    }

    @Operation(
            summary = "Actualizar una cuenta existente",
            description = "Busca la cuenta por su ID y modifica sus campos con los datos provistos en el DTO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuenta actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o error de sintaxis en el JSON"),
            @ApiResponse(responseCode = "404", description = "La cuenta que se intenta actualizar no fue encontrada")
    })
    @PutMapping("/update")
    public Account updateAccount(@RequestParam Long id, @RequestBody RequestAccountDTO accountDto) {
        return accountService.updateAccount(id, accountDto);
    }

    @Operation(
            summary = "Eliminar una cuenta por ID",
            description = "Elimina de forma permanente el registro de la cuenta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cuenta eliminada de forma exitosa"),
            @ApiResponse(responseCode = "404", description = "La cuenta que se intenta eliminar no existe")
    })
    @DeleteMapping("/delete")
    public void deleteAccountById(@RequestParam Long id) {
        accountService.deleteAccount(id);
    }

    @Operation(
            summary = "Obtener saldo de la cuenta en pesos",
            description = "Calcula el saldo actual de la cuenta especificada a su equivalente en pesos argentinos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Saldo obtenido y calculado exitosamente"),
            @ApiResponse(responseCode = "404", description = "La cuenta provista no existe")
    })
    @GetMapping("/{id}/saldo-pesos")
    public Double getSaldoEnPesos(@PathVariable Long id) {
        return accountService.getSaldoEnPesos(id);
    }
}
