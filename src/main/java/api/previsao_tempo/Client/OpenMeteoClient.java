package api.previsao_tempo.Client;


import api.previsao_tempo.Dto.Response.WeatherResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Component
public class OpenMeteoClient {

    private final RestClient restClient ;

    public OpenMeteoClient (RestClient.Builder builder) {
        restClient = builder
                .baseUrl("https://api.open-meteo.com")
                .build();
    }

    public WeatherResponse buscarPrevisao(
            double latitude,
            double longitude) {

        return restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/forecast")
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam(
                                "daily",
                                "temperature_2m_max,temperature_2m_min,precipitation_sum"
                        )
                        .queryParam("timezone", "auto")
                        .queryParam("forecast_days", 7)
                        .build())
                .retrieve()
                .body(WeatherResponse.class);
    }



}
