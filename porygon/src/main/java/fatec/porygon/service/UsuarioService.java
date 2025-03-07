package fatec.porygon.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import fatec.porygon.service.model.Usuario;
import fatec.porygon.service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository UsuarioRepository;

    private final String SECRET_KEY = "sua_chave_secreta"; // Mantenha isso em segredo
    private final long EXPIRATION_TIME = 86400000; // 1 dia em milissegundos

    public String authenticate(String Usuarionome, String senha) {
        Usuario Usuario = UsuarioRepository.findByUsuarionome(Usuarionome);
        if (Usuario != null && new BCryptSenhaEncoder().matches(senha, Usuario.getSenha())) {
            return generateToken(Usuario);
        }
        throw new RuntimeException("Usuário ou senha inválidos");
    }

    private String generateToken(Usuario Usuario) {
        return JWT.create()
                .withSubject(Usuario.getUsuarionome())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY.getBytes()));
    }

    // Método para criar novos usuários (opcional)
    public void registerUsuario(Usuario Usuario) {
        Usuario.setSenha(new BCryptPasswordEncoder().encode(Usuario.getSenha()));
        UsuarioRepository.save(Usuario);
    }
}
