package api.previsao_tempo.Service;


import api.previsao_tempo.Database.model.UsuarioEntity;
import api.previsao_tempo.Database.repository.UsuarioRepository;
import api.previsao_tempo.Dto.Request.UsuarioRequestDto;
import api.previsao_tempo.Exception.BadRequestException;
import api.previsao_tempo.Exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository ;
    private final PasswordEncoder passwordEncoder ;

    public void criarUsuario (UsuarioRequestDto usuarioRequestDto) {

       boolean usuario = usuarioRepository.existsByEmail(usuarioRequestDto.email());

       if(usuario){
           throw new BadRequestException("Email ja cadastrado");
       }

        usuarioRepository.save(UsuarioEntity.builder()
                .email(usuarioRequestDto.email())
                .senha(passwordEncoder.encode(usuarioRequestDto.senha()))
                .build());

    }
}
