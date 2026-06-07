package api_fintech.Users;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void deberiaCrearUsuario() {
        User userParaGuardar = new User();
        userParaGuardar.setNombre("Juan");
        userParaGuardar.setCorreoElectronico("juan@email.com");

        User userGuardado = new User();
        userGuardado.setId(1L);
        userGuardado.setNombre("Juan");
        userGuardado.setCorreoElectronico("juan@email.com");

        when(userRepository.save(any(User.class))).thenReturn(userGuardado);

        User result = userService.AddUser(userParaGuardar);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Juan", result.getNombre());
        assertEquals("juan@email.com", result.getCorreoElectronico());

        verify(userRepository).save(any(User.class));
    }
}