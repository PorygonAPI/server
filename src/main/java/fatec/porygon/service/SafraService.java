package fatec.porygon.service;

import fatec.porygon.entity.Safra;
import fatec.porygon.entity.Usuario;
import fatec.porygon.repository.SafraRepository;
import fatec.porygon.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.List;


@Service
public class SafraService {

    private final SafraRepository safraRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public SafraService(SafraRepository safraRepository, UsuarioRepository usuarioRepository) {
        this.safraRepository = safraRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Safra salvar(Safra safra) {
        return safraRepository.save(safra);
    }

    public Safra buscarPorId(Long id) {
        return safraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Safra não encontrada"));
    }

    public List<Safra> listarTodas() {
        return safraRepository.findAll();
    }

    public Safra atualizar(Long id, Safra safraAtualizada) {
        Safra existente = buscarPorId(id);
        existente.setAno(safraAtualizada.getAno());
        existente.setCultura(safraAtualizada.getCultura());
        existente.setTalhao(safraAtualizada.getTalhao());
        existente.setStatus(safraAtualizada.getStatus());
        existente.setArquivoDaninha(safraAtualizada.getArquivoDaninha());
        existente.setArquivoFinalDaninha(safraAtualizada.getArquivoFinalDaninha());
        return safraRepository.save(existente);
    }

    public void deletar(Long id) {
        safraRepository.deleteById(id);
    }

    public Safra associarAnalista(Long safraId, Long usuarioId) {
        Safra safra = buscarPorId(safraId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        safra.setUsuarioAnalista(usuario);
        return safraRepository.save(safra);
    }

    public Safra criarSafra(Safra safra) {
        return safraRepository.save(safra);
    }

    public List<Safra> buscarPorTalhao(Long talhaoId) {
        List<Safra> safras = safraRepository.findByTalhaoId(talhaoId);
        if (safras.isEmpty()) {
            throw new RuntimeException("Nenhuma safra encontrada para o talhão ID: " + talhaoId);
        }
        return safras;
    }
}