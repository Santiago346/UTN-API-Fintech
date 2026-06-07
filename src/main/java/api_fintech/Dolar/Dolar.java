package api_fintech.Dolar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dolar {

    private String moneda;
    private String casa;
    private String nombre;
    private Integer compra;
    private Integer venta;
    private LocalDate fechaActualizacion;
}
