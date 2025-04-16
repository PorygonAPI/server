package fatec.porygon.service;

import fatec.porygon.dto.TalhaoPendenteDto;
import fatec.porygon.entity.Safra;
import fatec.porygon.repository.TalhaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TalhaoService {

    private final TalhaoRepository talhaoRepository;

    public TalhaoService(TalhaoRepository talhaoRepository) {
        this.talhaoRepository = talhaoRepository;
    }

    public List<TalhaoPendenteDto> listarTalhoesPendentes() {
        return talhaoRepository.findDistinctBySafrasStatusAndSafrasUsuarioAnalistaIsNull(StatusSafra.Pendente).stream()
                .map(t -> {
                    Safra safra = t.getSafras().stream()
                            .filter(s -> s.getUsuarioAnalista() == null)
                            .findFirst()
                            .orElse(null);

                    if (safra == null) return null;

                    return new TalhaoPendenteDto(
                            t.getId(),
                            t.getAreaAgricola().getNomeFazenda(),
                            safra.getCultura().getNome(),
                            safra.getProdutividadeAno(),
                            t.getArea(),
                            t.getTipoSolo().getTipo(),
                            t.getAreaAgricola().getCidade().getNome(),
                            t.getAreaAgricola().getEstado()
                    );
                })
                .filter(Objects::nonNull)
                .toList();
    }
}

