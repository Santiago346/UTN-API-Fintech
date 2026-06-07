package api_fintech.Accounts;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import api_fintech.Users.User;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Integer numeroCuenta;
    private String moneda;
    private Double saldo;
    private Boolean activo;
    private LocalDate fechaCreacion;
    private LocalDate fechaModificacion;

}

