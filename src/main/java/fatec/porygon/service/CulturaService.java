package fatec.porygon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.porygon.entity.Cultura;
import fatec.porygon.repository.CulturaRepository;

@Service
public class CulturaService {

    @Autowired
    private CulturaRepository culturaRepository;

    public Cultura buscarOuCriar(String nome) {
        return culturaRepository.findByNome(nome)
                .orElseGet(() -> {
                    Cultura nova = new Cultura();
                    nova.setNome(nome);
                    return culturaRepository.save(nova);
                });
    }
}

