package api.previsao_tempo.Controller;


import api.previsao_tempo.Dto.Request.LoginRequest;
import api.previsao_tempo.Dto.Response.LoginResponse;
import api.previsao_tempo.Security.TokenService;
import api.previsao_tempo.Security.UsuarioDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService ;
    private final AuthenticationManager authenticationManager ;

    @PostMapping
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.senha()));

        UsuarioDetails usuarioDetails = (UsuarioDetails) authentication.getPrincipal();

        String token = tokenService.gerarToken(usuarioDetails);

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
