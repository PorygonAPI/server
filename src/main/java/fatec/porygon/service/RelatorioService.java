package fatec.porygon.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.porygon.dto.CulturaMaisProdutivaDto;
import fatec.porygon.dto.ProdutividadeMediaPorCulturaDto;
import fatec.porygon.dto.ProdutividadeMediaPorEstadoDto;
import fatec.porygon.dto.ProdutividadeMediaPorTipoSoloDto;
import fatec.porygon.dto.RankingEstadosDto;
import fatec.porygon.dto.RelatorioPorAnalistaDto;
import fatec.porygon.dto.RelatorioProdutividadeDto;
import fatec.porygon.dto.SafraRelatorioDto;
import fatec.porygon.dto.SafraRelatorioDto.DuracaoDto;
import fatec.porygon.dto.StatusRelatorioDto;
import fatec.porygon.entity.Safra;
import fatec.porygon.enums.StatusSafra;
import fatec.porygon.repository.SafraRepository;

@Service
public class RelatorioService {

    @Autowired
    private SafraRepository safraRepository;

    public StatusRelatorioDto getContagemPorStatus() {
        // TODO: Implementar lógica real
        return new StatusRelatorioDto(5, 10, 7);
    }

    public List<RelatorioPorAnalistaDto> getRelatorioPorAnalista() {
        // TODO: Implementar lógica real
        return List.of(
            new RelatorioPorAnalistaDto("João", 1, 2, 3),
            new RelatorioPorAnalistaDto("Maria", 0, 1, 5)
        );
    }

    public RelatorioProdutividadeDto gerarRelatorioProdutividade() {
        // TODO: Implementar lógica real sem filtros
        return new RelatorioProdutividadeDto(
            List.of(
            new ProdutividadeMediaPorCulturaDto("Soja", 3.5),
            new ProdutividadeMediaPorCulturaDto("Milho", 4.2)
            ),
            List.of(
            new ProdutividadeMediaPorEstadoDto("São Paulo", 5.0),
            new ProdutividadeMediaPorEstadoDto("Minas Gerais", 4.8)
            ),
            List.of(
            new ProdutividadeMediaPorTipoSoloDto("Argiloso", 4.0),
            new ProdutividadeMediaPorTipoSoloDto("Arenoso", 3.7)
            ),
            new CulturaMaisProdutivaDto("Soja", 3.5),
            List.of(
            new RankingEstadosDto("São Paulo", 5.0),
            new RankingEstadosDto("Minas Gerais", 4.8),
            new RankingEstadosDto("Paraná", 4.6),
            new RankingEstadosDto("Rio Grande do Sul", 4.4),
            new RankingEstadosDto("Goiás", 4.2)
            )
        );
    }
    public DuracaoDto calcularDuracao(LocalDateTime inicio, LocalDateTime fim) {
    if (inicio == null || fim == null) return null;
    Duration duracao = Duration.between(inicio, fim);
    long dias = duracao.toDays();
    long horas = duracao.toHours() % 24;
    long minutos = duracao.toMinutes() % 60;
    return new DuracaoDto(dias, horas, minutos);
}

    public List<SafraRelatorioDto> gerarRelatorioSafrasAprovadas() {
        List<Safra> safrasAprovadas = safraRepository.findByStatus(StatusSafra.Aprovado);

        return safrasAprovadas.stream().map(safra -> {
            DuracaoDto duracao = calcularDuracao(
                    safra.getDataCadastro(), safra.getDataAprovacao()
            );

            return new SafraRelatorioDto(
                    safra.getId(),
                    safra.getUsuarioAnalista().getNome(),
                    safra.getDataCadastro(),
                    safra.getDataAtribuicao(),
                    safra.getDataAprovacao(),
                    duracao
            );
        }).toList();
    }
    
    public List<SafraRelatorioDto.MediaAnalistaDto> calcularMediaPorAnalista() {
        List<Safra> safrasAprovadas = safraRepository.findByStatus(StatusSafra.Aprovado);

        Map<Long, List<Safra>> safrasPorAnalistaId = safrasAprovadas.stream()
            .filter(s -> s.getUsuarioAnalista() != null)
            .collect(Collectors.groupingBy(s -> s.getUsuarioAnalista().getId()));
        return safrasPorAnalistaId.entrySet().stream()
            .map(entry -> {
                Long idAnalista = entry.getKey();
                List<Safra> safrasDoAnalista = entry.getValue();

                if (safrasDoAnalista.isEmpty()) {
                    return null;
                }

                String nomeAnalista = safrasDoAnalista.get(0).getUsuarioAnalista().getNome();

                long totalSegundos = safrasDoAnalista.stream()
                    .filter(s -> s.getDataCadastro() != null && s.getDataAprovacao() != null)
                    .mapToLong(s -> Duration.between(s.getDataCadastro(), s.getDataAprovacao()).getSeconds())
                    .sum();

                long quantidade = safrasDoAnalista.stream()
                    .filter(s -> s.getDataCadastro() != null && s.getDataAprovacao() != null)
                    .count();

                if (quantidade == 0) {
                    return null;
                }

                long mediaSegundos = totalSegundos / quantidade;
                Duration mediaDuracao = Duration.ofSeconds(mediaSegundos);

                SafraRelatorioDto.DuracaoDto duracaoDto = new SafraRelatorioDto.DuracaoDto(
                    mediaDuracao.toDays(),
                    mediaDuracao.toHours() % 24,
                    mediaDuracao.toMinutes() % 60
                );

                return new SafraRelatorioDto.MediaAnalistaDto(idAnalista, nomeAnalista, duracaoDto);
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        }
}

