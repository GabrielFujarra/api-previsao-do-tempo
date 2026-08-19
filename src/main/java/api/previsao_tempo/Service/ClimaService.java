package api.previsao_tempo.Service;


import api.previsao_tempo.Client.OpenMeteoClient;
import api.previsao_tempo.Client.OpenMeteoGeocodingClient;
import api.previsao_tempo.Dto.Response.GeocodingResponse;
import api.previsao_tempo.Dto.Response.PrevisaoResponse;
import api.previsao_tempo.Dto.Response.WeatherResponse;
import api.previsao_tempo.Exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClimaService {

    private final OpenMeteoGeocodingClient geocodingClient;
    private final OpenMeteoClient openMeteoClient;

    public PrevisaoResponse buscarPrevisao(String cidade) {

        GeocodingResponse geocodingResponse =
                geocodingClient.buscarCidade(cidade);

        if (geocodingResponse.results() == null
                || geocodingResponse.results().isEmpty()) {

            throw new BadRequestException(
                    "Cidade não encontrada: " + cidade
            );
        }

        GeocodingResponse.CidadeResponse local =
                geocodingResponse.results().getFirst();

        WeatherResponse weatherResponse =
                openMeteoClient.buscarPrevisao(
                        local.latitude(),
                        local.longitude()
                );

        List<PrevisaoResponse.PrevisaoDia> previsao =
                new ArrayList<>();

        for (int i = 0; i < weatherResponse.daily().time().size(); i++) {

            PrevisaoResponse.PrevisaoDia dia =
                    new PrevisaoResponse.PrevisaoDia(
                            weatherResponse.daily().time().get(i),
                            weatherResponse.daily().temperature_2m_max().get(i),
                            weatherResponse.daily().temperature_2m_min().get(i),
                            weatherResponse.daily().precipitation_sum().get(i)
                    );

            previsao.add(dia);
        }

        return new PrevisaoResponse(
                local.name(),
                local.country(),
                local.timezone(),
                previsao
        );
    }
}
