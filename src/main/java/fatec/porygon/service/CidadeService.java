package fatec.porygon.service;

import fatec.porygon.entity.Cidade;
import fatec.porygon.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    @Autowired
    public CidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    @Transactional
    public Cidade buscarOuCriar(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da cidade não pode ser nulo ou vazio");
        }
        
        String nomeTrimmed = nome.trim();
        Optional<Cidade> cidadeOpt = cidadeRepository.findByNome(nomeTrimmed);
        
        if (cidadeOpt.isPresent()) {
            return cidadeOpt.get();
        } else {
            Cidade novaCidade = new Cidade();
            novaCidade.setNome(nomeTrimmed);
            return cidadeRepository.save(novaCidade);
        }
    }
    
    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Analista') or hasAuthority('Consultor')")
    public List<Cidade> listarTodas() {
        return cidadeRepository.findAll();
    }
}
