package api_fintech.Accounts;

import api_fintech.Accounts.Dtos.AccountResponse;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Obtener todas las cuentas")
    @GetMapping("/list")
    public List<Account> getAccounts() {
        return accountService.getAllAccounts();
    }

    @Operation(summary = "Obtener una cuenta por ID")
    @GetMapping("/id")
    public AccountResponse getAccountById(@RequestParam Long id) {
        return accountService.getAccount(id);
    }

    @Operation(summary = "Crear una cuenta")
    @PostMapping("/add")
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @Operation(summary = "Actualizar una cuenta")
    @PutMapping("/update")
    public Account updateAccount(@RequestBody Account account, @RequestParam Long id) {
        return accountService.updateAccount(id, account);
    }

    @Operation(summary = "Eliminar una cuenta")
    @DeleteMapping("/delete")
    public void deleteAccountById(@RequestParam Long id) {
        accountService.deleteAccount(id);
    }

    @Operation(summary = "Obtener saldo de la cuenta en pesos")
    @GetMapping("/{id}/saldo-pesos")
    public Double getSaldoEnPesos(@PathVariable Long id) {
        return accountService.getSaldoEnPesos(id);
    }
}
