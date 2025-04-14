package fatec.porygon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.porygon.entity.TipoSolo;
import fatec.porygon.repository.TipoSoloRepository;

@Service
public class TipoSoloService {

    @Autowired
    private TipoSoloRepository tipoSoloRepository;

    public TipoSolo buscarOuCriar(String nome) {
        return tipoSoloRepository.findByTipoSolo(nome)
                .orElseGet(() -> {
                    TipoSolo novo = new TipoSolo();
                    novo.setTipoSolo(nome);
                    return tipoSoloRepository.save(novo);
                });
    }
}

