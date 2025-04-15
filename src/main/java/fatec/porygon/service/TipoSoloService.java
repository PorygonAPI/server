package fatec.porygon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.porygon.entity.TipoSolo;
import fatec.porygon.repository.TipoSoloRepository;

@Service
public class TipoSoloService {

    @Autowired
    private TipoSoloRepository tipoSoloRepository;

    // public TipoSolo buscarOuCriar(String tipoSolo) {
    //     return tipoSoloRepository.findByTipo(tipoSolo)
    //             .orElseGet(() -> {
    //                 TipoSolo novo = new TipoSolo();
    //                 novo.setTipo(tipoSolo);
    //                 return tipoSoloRepository.save(novo);
    //             });
    // }

    @Transactional
    public TipoSolo buscarOuCriar(String tipoSolo) {
        if (tipoSolo == null || tipoSolo.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipoSolo do solo não pode ser nulo ou vazio");
        }
        
        String tipoSoloTrimmed = tipoSolo.trim();
        Optional<TipoSolo> tipoSoloOpt = tipoSoloRepository.findByTipo(tipoSoloTrimmed);
        
        if (tipoSoloOpt.isPresent()) {
            return tipoSoloOpt.get();
        } else {
            TipoSolo novoTipoSolo = new TipoSolo();
            novoTipoSolo.setTipo(tipoSoloTrimmed);
            return tipoSoloRepository.save(novoTipoSolo);
        }
    }
}

