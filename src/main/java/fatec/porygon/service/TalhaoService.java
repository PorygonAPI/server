package fatec.porygon.service;

import fatec.porygon.dto.TalhaoPendenteDto;
import fatec.porygon.entity.Safra;
import fatec.porygon.repository.TalhaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import fatec.porygon.enums.StatusSafra;

import java.time.LocalDateTime;
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

    public void salvarEdicaoTalhao(Long idTalhao) {
        var talhao = talhaoRepository.findById(idTalhao)
                .orElseThrow(() -> new EntityNotFoundException("Talhão não encontrado"));

        var safra = talhao.getSafras().stream()
                .filter(s -> s.getUsuarioAnalista() != null && s.getStatus() == StatusSafra.Atribuido)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Safra pendente não encontrada para esse talhão"));

        safra.setDataUltimaVersao(LocalDateTime.now());
        talhaoRepository.save(talhao);
    }

    public void aprovarTalhao(Long idTalhao) {
        var talhao = talhaoRepository.findById(idTalhao)
                .orElseThrow(() -> new EntityNotFoundException("Talhão não encontrado"));

        var safra = talhao.getSafras().stream()
                .filter(s -> s.getUsuarioAnalista() != null && s.getStatus() == StatusSafra.Atribuido)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Safra pendente não encontrada para esse talhão"));

        safra.setStatus(StatusSafra.Aprovado);
        safra.setDataUltimaVersao(LocalDateTime.now());
        talhaoRepository.save(talhao);
    }

}

