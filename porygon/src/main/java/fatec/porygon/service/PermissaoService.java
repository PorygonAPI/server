package fatec.porygon.service;

import fatec.porygon.entity.Permissao;
import fatec.porygon.repository.PermissaoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PermissaoService {

    private final PermissaoRepository permissaoRepository;

    public PermissaoService(PermissaoRepository permissaoRepository){
        this.permissaoRepository = permissaoRepository;
    }

    public List<Permissao> buscarTodos(){
        return permissaoRepository.findAll();
    }

}
