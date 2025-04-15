package fatec.porygon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.porygon.entity.TipoSolo;
import fatec.porygon.repository.TipoSoloRepository;

@Service
public class TipoSoloService {

    @Autowired
    private TipoSoloRepository tipoSoloRepository;

    // public TipoSolo buscarOuCriar(String tipo) {
    //     return tipoSoloRepository.findByTipo(tipo)
    //             .orElseGet(() -> {
    //                 TipoSolo novo = new TipoSolo();
    //                 novo.setTipo(tipo);
    //                 return tipoSoloRepository.save(novo);
    //             });
    // }

    @Transactional
    public TipoSolo buscarOuCriar(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo do solo não pode ser nulo ou vazio");
        }
        
        String tipoTrimmed = tipo.trim();
        Optional<TipoSolo> tipoSoloOpt = tipoSoloRepository.findByTipo(tipoTrimmed);
        
        if (tipoSoloOpt.isPresent()) {
            return tipoSoloOpt.get();
        } else {
            TipoSolo novoTipoSolo = new TipoSolo();
            novoTipoSolo.setTipo(tipoTrimmed);
            return tipoSoloRepository.save(novoTipoSolo);
        }
    }
}

