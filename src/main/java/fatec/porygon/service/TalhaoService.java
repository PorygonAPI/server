package fatec.porygon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fatec.porygon.entity.Talhao;
import fatec.porygon.entity.Usuario;
import fatec.porygon.repository.TalhaoRepository;
import fatec.porygon.repository.UsuarioRepository;

@Service
public class TalhaoService {

    @Autowired
    private TalhaoRepository talhaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void atribuirAnalista(Long talhaoId, Long usuarioAnalistaId) {
        Talhao talhao = talhaoRepository.findById(talhaoId)
                .orElseThrow(() -> new RuntimeException("Talhão não encontrado."));
        Usuario analista = usuarioRepository.findById(usuarioAnalistaId)
                .orElseThrow(() -> new RuntimeException("Usuário analista não encontrado."));

        if (talhao.getUsuarioAnalista() != null &&
            talhao.getUsuarioAnalista().getId().equals(usuarioAnalistaId)) {
            throw new RuntimeException("Este talhão já está associado a esse analista.");
        }

        talhao.setUsuarioAnalista(analista);
        talhaoRepository.save(talhao);
    }
}