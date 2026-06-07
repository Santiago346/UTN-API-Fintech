package api_fintech.Accounts;

import api_fintech.Accounts.Dtos.AccountResponseDTO;
import api_fintech.Accounts.Dtos.RequestAccountDTO;
import api_fintech.Dolar.Dolar;
import api_fintech.Dolar.DolarService;
import api_fintech.Users.User;
import api_fintech.Users.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final DolarService dolarService;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository , DolarService dolarService, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.dolarService = dolarService;
        this.userRepository = userRepository;
    }

    public AccountResponseDTO getAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        return new AccountResponseDTO(
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

    public Account createAccount(RequestAccountDTO dto) {

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User no encontrado"));

        Account account = Account.builder()
                .user(user)
                .numeroCuenta(dto.numeroCuenta())
                .moneda(dto.moneda())
                .saldo(dto.saldo())
                .activo(dto.activo())
                .fechaCreacion(dto.fechaCreacion())
                .fechaModificacion(dto.fechaModificacion())
                .build();

        return accountRepository.save(account);
    }

    public Account updateAccount(Long accountId, RequestAccountDTO dto) {
        Account accountExistente = findAccountById(accountId);

        accountExistente.setNumeroCuenta(dto.numeroCuenta());
        accountExistente.setMoneda(dto.moneda());
        accountExistente.setSaldo(dto.saldo());
        accountExistente.setActivo(dto.activo());
        accountExistente.setFechaModificacion(LocalDate.now());

        return accountRepository.save(accountExistente);
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
