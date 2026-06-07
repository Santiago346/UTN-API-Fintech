package api_fintech.Users;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellidoOrazonSocial;
    private String documentoOCuit;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private String fechaAlta;
}