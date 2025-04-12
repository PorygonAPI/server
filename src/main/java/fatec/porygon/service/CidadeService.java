package fatec.porygon.service;

import fatec.porygon.entity.Cidade;
import fatec.porygon.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    public List<Cidade> listarTodas() {
        return cidadeRepository.findAll();
    }
    
    public Optional<Cidade> buscarPorId(Long id) {
        return cidadeRepository.findById(id);
    }
    
    @Transactional
    public Cidade atualizar(Long id, String novoNome) {
        Optional<Cidade> cidadeOpt = cidadeRepository.findById(id);
        if (cidadeOpt.isPresent()) {
            Cidade cidade = cidadeOpt.get();
            cidade.setNome(novoNome);
            return cidadeRepository.save(cidade);
        } else {
            throw new RuntimeException("Cidade não encontrada com ID: " + id);
        }
    }
    
    @Transactional
    public void excluir(Long id) {
        if (!cidadeRepository.existsById(id)) {
            throw new RuntimeException("Cidade não encontrada com ID: " + id);
        }
        cidadeRepository.deleteById(id);
    }
}
