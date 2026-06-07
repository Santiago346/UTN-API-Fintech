package api_fintech.Accounts.Dtos;

import java.time.LocalDate;

public record RequestAccountDTO(
        Long userId,
        Integer numeroCuenta,
        String moneda,
        Double saldo,
        Boolean activo,
        LocalDate fechaCreacion,
        LocalDate fechaModificacion
) {}
