package api_fintech.Account;

import api_fintech.Accounts.Account;
import api_fintech.Accounts.AccountRepository;
import api_fintech.Accounts.AccountService;
import api_fintech.Accounts.Dtos.AccountResponse;
import api_fintech.Users.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void deberiaRetornarCuenta() {

        User user = new User();
        user.setId(10L);

        Account account = new Account();
        account.setId(1L);
        account.setUser(user);
        account.setNumeroCuenta(123456);
        account.setMoneda("USD");
        account.setSaldo(1000.0);
        account.setActivo(true);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        AccountResponse result = accountService.getAccount(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(10L, result.userId());
        assertEquals("USD", result.moneda());
    }

    @Test
    void deberiaLanzarExcepcionSiNoExisteLaCuenta() {

        when(accountRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> accountService.getAccount(1L)
        );

        assertEquals("Cuenta no encontrada", exception.getMessage());
    }

    @Test
    void deberiaRetornarTodasLasCuentas() {

        Account account1 = new Account();
        account1.setId(1L);

        Account account2 = new Account();
        account2.setId(2L);

        List<Account> accounts = List.of(account1, account2);

        when(accountRepository.findAll())
                .thenReturn(accounts);

        List<Account> result = accountService.getAllAccounts();

        assertEquals(2, result.size());
        verify(accountRepository).findAll();
    }

    @Test
    void deberiaCrearCuenta() {

        Account account = new Account();
        account.setId(1L);

        when(accountRepository.save(account))
                .thenReturn(account);

        Account result = accountService.createAccount(account);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(accountRepository).save(account);
    }

    @Test
    void deberiaActualizarCuenta() {

        Account oldAccount = new Account();
        oldAccount.setId(1L);

        Account updatedAccount = new Account();
        updatedAccount.setId(1L);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(oldAccount));

        when(accountRepository.save(updatedAccount))
                .thenReturn(updatedAccount);

        Account result = accountService.updateAccount(1L, updatedAccount);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(accountRepository).findById(1L);
        verify(accountRepository).save(updatedAccount);
    }

    @Test
    void deberiaEliminarCuenta() {

        Account account = new Account();
        account.setId(1L);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        accountService.deleteAccount(1L);

        verify(accountRepository).findById(1L);
        verify(accountRepository).delete(account);
    }
}
