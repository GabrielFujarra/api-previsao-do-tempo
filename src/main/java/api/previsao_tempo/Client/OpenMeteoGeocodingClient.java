package api.previsao_tempo.Client;

import api.previsao_tempo.Dto.Response.GeocodingResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Component
public class OpenMeteoGeocodingClient {

    private final RestClient restClient ;

    public OpenMeteoGeocodingClient (RestClient.Builder builder){
        restClient = builder
                .baseUrl("https://geocoding-api.open-meteo.com")
                .build();

    }

    public GeocodingResponse buscarCidade (String cidade) {

        return restClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/search")
                        .queryParam("name",cidade)
                        .queryParam("count",1)
                        .queryParam("language","pt")
                        .queryParam("format","json")
                        .build())
                .retrieve()
                .body(GeocodingResponse.class);

    }
}

