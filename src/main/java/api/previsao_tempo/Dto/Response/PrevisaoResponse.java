package api.previsao_tempo.Dto.Response;

import java.util.List;

public record PrevisaoResponse(String cidade,
                               String pais,
                               String timezone,
                               List<PrevisaoDia> previsao) {

    public record PrevisaoDia(
            String data,
            Double temperaturaMaxima,
            Double temperaturaMinima,
            Double precipitacao
    ){

    }
}
