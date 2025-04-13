package main.java.fatec.porygon.service;

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

