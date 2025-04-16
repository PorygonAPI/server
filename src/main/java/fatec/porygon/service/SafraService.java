package fatec.porygon.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.porygon.entity.Safra;
import fatec.porygon.repository.SafraRepository;

@Service
public class SafraService {

    @Autowired
    private SafraRepository safraRepository;

    public Safra criarSafra(Safra safra) {
        return safraRepository.save(safra);
    }

    public Optional<Safra> buscarPorTalhao(Long talhaoId) {
    return safraRepository.findByTalhaoId(talhaoId);
}
}

