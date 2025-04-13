package main.java.fatec.porygon.service;

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

