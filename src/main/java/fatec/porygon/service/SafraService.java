package main.java.fatec.porygon.service;

@Service
public class SafraService {

    @Autowired
    private SafraRepository safraRepository;

    public Safra criarSafra(Safra safra) {
        return safraRepository.save(safra);
    }
}

