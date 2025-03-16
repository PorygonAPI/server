package fatec.porygon.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import fatec.porygon.dto.UsuarioDto;
import fatec.porygon.entity.Usuario;
import fatec.porygon.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AutenticacaoService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final String SECRET = "porygon123";

    public AutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String autenticar(UsuarioDto usuarioDto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(usuarioDto.getEmail());
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
//            if (passwordEncoder.matches(usuarioDto.getSenha(), usuario.getSenha())) {
            return gerarToken(usuario);
//            }
        }
        throw new RuntimeException("Usuário ou senha inválidos");
    }

    private String gerarToken(Usuario usuario) {
        return JWT.create()
                .withSubject(usuario.getNome())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(Algorithm.HMAC256(SECRET));
    }
}
