package api_fintech.Dolar;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DolarController {

    private final DolarService dolarService;

    @GetMapping("/dolar")
    public Dolar getCotizacion() {
        return dolarService.getCotizacion();
    }
}
