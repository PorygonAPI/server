package fatec.porygon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import fatec.porygon.entity.Cultura;
import fatec.porygon.repository.CulturaRepository;

@Service
public class CulturaService {

    @Autowired
    private final CulturaRepository culturaRepository;

    @Autowired
    public CulturaService(CulturaRepository culturaRepository) {
        this.culturaRepository = culturaRepository;
    }

    @Transactional
    public Cultura buscarOuCriar(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da cultura não pode ser nulo ou vazio");
        }
        
        String nomeTrimmed = nome.trim();
        Optional<Cultura> culturaOpt = culturaRepository.findByNome(nomeTrimmed);
        
        if (culturaOpt.isPresent()) {
            return culturaOpt.get();
        } else {
            Cultura novaCultura = new Cultura();
            novaCultura.setNome(nomeTrimmed);
            return culturaRepository.save(novaCultura);
        }
    }
}


