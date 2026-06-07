package api_fintech.Dolar;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class DolarService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.dolar.api.url}")
    private String dolarApiUrl;

    public Dolar getCotizacion() {
        return restTemplate.getForObject(dolarApiUrl, Dolar.class);
    }
}
