package fatec.porygon.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import fatec.porygon.dto.UsuarioDto;
import fatec.porygon.entity.Usuario;
import fatec.porygon.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AutenticacaoService {
    private final UsuarioRepository usuarioRepository;
    private final String SECRET = "porygon123";
    private final BCryptPasswordEncoder passwordEncoder;


    public AutenticacaoService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Map<String, Object> autenticar(UsuarioDto usuarioDto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(usuarioDto.getEmail());
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(usuarioDto.getSenha(), usuario.getSenha())) {
                return gerarDadosUsuario(usuario);
            }
        }
        throw new RuntimeException("Usuário ou senha inválidos");
    }

    private Map<String, Object> gerarDadosUsuario(Usuario usuario) {
        Map<String, Object> response = new HashMap<>();

        String role = usuario.getCargo().getNome();

        String token = JWT.create()
                .withSubject(String.valueOf(usuario.getId()))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .withClaim("roles", Collections.singletonList(role))
                .sign(Algorithm.HMAC256(SECRET));
        response.put("token", token);
        response.put("role", role);
        response.put("id", usuario.getId());
        response.put("nome", usuario.getNome());
        return response;
    }
}
