package api_fintech.Dolar;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dolar")
@Tag(name = "Dolar", description = "Obtener cotizacion por dolar MEP")
public class DolarController {

    private final DolarService dolarService;

    @GetMapping("/cotizacion")
    public Dolar getCotizacion() {
        return dolarService.getCotizacion();
    }
}
