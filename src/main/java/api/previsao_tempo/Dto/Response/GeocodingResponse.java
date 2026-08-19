package api.previsao_tempo.Dto.Response;

import java.util.List;

public record GeocodingResponse(List<CidadeResponse> results) {

    public record CidadeResponse (String name,
                                  double latitude,
                                  double longitude,
                                  String country,
                                  String country_code,
                                  String timezone){

    }
}
