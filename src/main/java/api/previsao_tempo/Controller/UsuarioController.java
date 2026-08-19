package api.previsao_tempo.Controller;


import api.previsao_tempo.Dto.Request.UsuarioRequestDto;
import api.previsao_tempo.Service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Void> criarUsuario (@RequestBody UsuarioRequestDto usuarioRequestDto) {
        usuarioService.criarUsuario(usuarioRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
