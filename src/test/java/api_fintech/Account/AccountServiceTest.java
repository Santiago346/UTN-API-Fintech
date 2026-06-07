package api_fintech.Account;

import api_fintech.Accounts.Account;
import api_fintech.Accounts.AccountRepository;
import api_fintech.Accounts.AccountService;
import api_fintech.Accounts.Dtos.AccountResponseDTO;
import api_fintech.Accounts.Dtos.RequestAccountDTO;
import api_fintech.Users.User;
import api_fintech.Users.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

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

        AccountResponseDTO result = accountService.getAccount(1L);

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
        Long userId = 1L;

        RequestAccountDTO requestDto = new RequestAccountDTO(
                userId, 123456, "ARS", 1000.0, true, LocalDate.now(), LocalDate.now()
        );

        User userMock = new User();
        userMock.setId(userId);

        Account accountGuardada = Account.builder()
                .id(1L)
                .user(userMock)
                .numeroCuenta(requestDto.numeroCuenta())
                .moneda(requestDto.moneda())
                .saldo(requestDto.saldo())
                .activo(requestDto.activo())
                .fechaCreacion(requestDto.fechaCreacion())
                .fechaModificacion(requestDto.fechaModificacion())
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(userMock));

        when(accountRepository.save(any(Account.class))).thenReturn(accountGuardada);

        Account result = accountService.createAccount(requestDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ARS", result.getMoneda());

        verify(userRepository).findById(userId);
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void deberiaActualizarCuenta() {
        Long accountId = 1L;

        RequestAccountDTO requestDto = new RequestAccountDTO(
                1L, 987654, "USD", 5000.0, true, LocalDate.now(), LocalDate.now()
        );

        Account oldAccount = Account.builder()
                .id(accountId)
                .numeroCuenta(123456)
                .moneda("ARS")
                .saldo(1000.0)
                .activo(true)
                .build();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(oldAccount));

        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account result = accountService.updateAccount(accountId, requestDto);

        assertNotNull(result);
        assertEquals(accountId, result.getId());
        assertEquals(987654, result.getNumeroCuenta());
        assertEquals("USD", result.getMoneda());
        assertEquals(5000.0, result.getSaldo());

        verify(accountRepository).findById(accountId);
        verify(accountRepository).save(any(Account.class));
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
