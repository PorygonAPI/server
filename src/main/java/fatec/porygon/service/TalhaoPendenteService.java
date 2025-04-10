package fatec.porygon.service;

import fatec.porygon.dto.TalhaoPendenteDTO;
import fatec.porygon.entity.Safra;
import fatec.porygon.entity.Talhao;
import fatec.porygon.repository.TalhaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TalhaoPendenteService {

    @Autowired
    private TalhaoPendenteRepository talhaoPendenteRepository;

    public List<TalhaoPendenteDto> listarTalhoesPendentes() {
        List<Talhao> talhoes = talhaoPendenteRepository.buscarTalhoesPendentes();

        return talhoes.stream().map(t -> {
            Optional<Safra> safraMaisRecente = t.getSafras().stream()
                .max(Comparator.comparing(Safra::getAno));

            String cultura = safraMaisRecente
                .map(s -> s.getCultura().getNome())
                .orElse("Sem safra");

            return new TalhaoPendenteDto(
                t.getId(),
                t.getArea_agricola_id().getNome_fazenda(),
                cultura,
                t.getProdutividade_ano(),
                t.getArea(),
                t.getTipo_solo().getNome(),
                t.getArea_agricola_id().getCidade_id().getNome(),
                t.getArea_agricola_id().getEstado()
            );
        }).toList();
    }
}
