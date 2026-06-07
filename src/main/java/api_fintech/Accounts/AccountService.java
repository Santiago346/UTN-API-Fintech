package api_fintech.Accounts;

import api_fintech.Accounts.Dtos.AccountResponse;
import api_fintech.Dolar.Dolar;
import api_fintech.Dolar.DolarService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final DolarService dolarService;

    public AccountService(AccountRepository accountRepository , DolarService dolarService) {
        this.accountRepository = accountRepository;
        this.dolarService = dolarService;
    }

    public AccountResponse getAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        return new AccountResponse(
                account.getId(),
                account.getUser().getId(),
                account.getNumeroCuenta(),
                account.getMoneda(),
                account.getSaldo(),
                account.getActivo(),
                account.getFechaCreacion(),
                account.getFechaModificacion()
        );
    }

    private Account findAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(Account account) {
        try {
            return accountRepository.save(account);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la cuenta", e);
        }
    }

    public Account updateAccount(Long accountId, Account account) {
        Account oldAccount = findAccountById(accountId);
        return accountRepository.save(account);
    }

    public void deleteAccount(Long accountId) {
        try {
            Account account = findAccountById(accountId);
            accountRepository.delete(account);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar la cuenta", e);
        }
    }

    public Double getSaldoEnPesos(Long accountId) {

        Account account = findAccountById(accountId);

        if ("ARS".equalsIgnoreCase(account.getMoneda())) {
            return account.getSaldo();
        }

        Dolar dolarMep = dolarService.getCotizacion();

        return account.getSaldo() * dolarMep.getCompra();
    }
}
