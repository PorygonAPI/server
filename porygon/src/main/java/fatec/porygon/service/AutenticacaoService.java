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
    private final BCryptPasswordEncoder passwordEncoder;
    private final String SECRET = "porygon123";

    public AutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Map<String, Object> autenticar(UsuarioDto usuarioDto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(usuarioDto.getEmail());
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (Objects.equals(usuarioDto.getSenha(), usuario.getSenha())) {
                return gerarDadosUsuario(usuario);
            }
        }
        throw new RuntimeException("Usuário ou senha inválidos");
    }

    private Map<String, Object> gerarDadosUsuario(Usuario usuario) {
        Map<String, Object> response = new HashMap<>();

        String role = usuario.getCargo().getNome();

        String token = JWT.create()
                .withSubject(usuario.getNome())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .withClaim("roles", Collections.singletonList(role)) // Adiciona a role como uma claim
                .sign(Algorithm.HMAC256(SECRET));
        response.put("token", token);
        response.put("role", role);
        response.put("nome", usuario.getNome());
        return response;
    }
}
