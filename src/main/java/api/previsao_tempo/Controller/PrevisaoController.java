package api.previsao_tempo.Controller;

import api.previsao_tempo.Dto.Response.PrevisaoResponse;
import api.previsao_tempo.Dto.Response.WeatherResponse;
import api.previsao_tempo.Service.ClimaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/previsao")
@RequiredArgsConstructor
public class PrevisaoController {

    private final ClimaService ClimaService;

    @GetMapping("/{cidade}")
    public PrevisaoResponse buscarPrevisao(
            @PathVariable String cidade) {

        return ClimaService.buscarPrevisao(cidade);
    }


}
