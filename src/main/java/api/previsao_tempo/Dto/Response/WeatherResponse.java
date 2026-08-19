package api.previsao_tempo.Dto.Response;

import java.util.List;

public record WeatherResponse(double latitude,
                              double longitude,
                              String timezone,
                              Daily daily) {

    public record Daily (List<String> time,
                         List<Double> temperature_2m_max,
                         List<Double> temperature_2m_min,
                         List<Double> precipitation_sum){

    }
}
