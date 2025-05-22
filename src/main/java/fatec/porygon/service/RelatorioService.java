package fatec.porygon.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
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
import fatec.porygon.entity.Usuario;
import fatec.porygon.enums.StatusSafra;
import fatec.porygon.repository.SafraRepository;

@Service
public class RelatorioService {

    private final SafraRepository safraRepository;

    public RelatorioService(SafraRepository safraRepository) {
        this.safraRepository = safraRepository;
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    public StatusRelatorioDto getContagemPorStatus(LocalDate dataInicial, LocalDate dataFinal) {
        List<Safra> safras;

        if (dataInicial != null && dataFinal != null) {
            safras = safraRepository.findAll().stream()
                    .filter(s -> {
                        LocalDate dataCadastro = s.getDataCadastro().toLocalDate();
                        return (!dataCadastro.isBefore(dataInicial) && !dataCadastro.isAfter(dataFinal));
                    })
                    .toList();
        } else {
            safras = safraRepository.findAll();
        }

        int pendentes = (int) safras.stream().filter(s -> s.getStatus() == StatusSafra.Pendente).count();
        int atribuidos = (int) safras.stream().filter(s -> s.getStatus() == StatusSafra.Atribuido).count();
        int aprovados = (int) safras.stream().filter(s -> s.getStatus() == StatusSafra.Aprovado).count();

        return new StatusRelatorioDto(pendentes, atribuidos, aprovados);
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    public List<RelatorioPorAnalistaDto> getRelatorioPorAnalista(LocalDate dataInicial, LocalDate dataFinal) {
        List<Safra> safras;

        if (dataInicial != null && dataFinal != null) {
            safras = safraRepository.findAll().stream()
                    .filter(s -> {
                        LocalDate dataCadastro = s.getDataCadastro().toLocalDate();
                        return (!dataCadastro.isBefore(dataInicial) && !dataCadastro.isAfter(dataFinal));
                    })
                    .toList();
        } else {
            safras = safraRepository.findAll();
        }

        Map<Usuario, List<Safra>> safrasPorAnalista = safras.stream()
                .filter(s -> s.getUsuarioAnalista() != null)
                .collect(Collectors.groupingBy(Safra::getUsuarioAnalista));

        List<RelatorioPorAnalistaDto> relatorio = new ArrayList<>();

        for (Map.Entry<Usuario, List<Safra>> entry : safrasPorAnalista.entrySet()) {
            Usuario analista = entry.getKey();
            List<Safra> listaSafras = entry.getValue();

            int pendentes = (int) listaSafras.stream().filter(s -> s.getStatus() == StatusSafra.Pendente).count();
            int atribuidos = (int) listaSafras.stream().filter(s -> s.getStatus() == StatusSafra.Atribuido).count();
            int aprovados = (int) listaSafras.stream().filter(s -> s.getStatus() == StatusSafra.Aprovado).count();

            relatorio.add(new RelatorioPorAnalistaDto(analista.getNome(), pendentes, atribuidos, aprovados));
        }

        return relatorio;
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    public RelatorioProdutividadeDto gerarRelatorioProdutividade() {
        List<ProdutividadeMediaPorCulturaDto> mediasPorCultura = mediaPorCultura();
        List<ProdutividadeMediaPorEstadoDto> mediasPorEstado = mediaPorEstado();
        List<ProdutividadeMediaPorTipoSoloDto> mediasPorTipoSolo = mediaPorTipoSolo();

        CulturaMaisProdutivaDto culturaMaisProdutiva = mediasPorCultura.stream()
                .max(Comparator.comparingDouble(ProdutividadeMediaPorCulturaDto::getProdutividadeMedia))
                .map(dto -> new CulturaMaisProdutivaDto(dto.getNomeCultura(), dto.getProdutividadeMedia()))
                .orElse(null);

        List<RankingEstadosDto> rankingEstados = mediasPorEstado.stream()
                .sorted(Comparator.comparingDouble(ProdutividadeMediaPorEstadoDto::getProdutividadeMedia).reversed())
                .map(dto -> new RankingEstadosDto(dto.getNomeEstado(), dto.getProdutividadeMedia()))
                .toList();

        return new RelatorioProdutividadeDto(
                mediasPorCultura,
                mediasPorEstado,
                mediasPorTipoSolo,
                culturaMaisProdutiva,
                rankingEstados);
    }

    public List<ProdutividadeMediaPorCulturaDto> mediaPorCultura() {
        List<Safra> safras = Optional.ofNullable(safraRepository.findAll()).orElse(List.of());

        return safras.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getCultura().getNome(),
                        Collectors.averagingDouble(Safra::getProdutividadeAno)))
                .entrySet().stream()
                .map(e -> new ProdutividadeMediaPorCulturaDto(
                        e.getKey(),
                        Math.round(e.getValue() * 100.0) / 100.0 
                ))
                .toList();
    }

    public List<ProdutividadeMediaPorEstadoDto> mediaPorEstado() {
        List<Safra> safras = Optional.ofNullable(safraRepository.findAll()).orElse(List.of());

        return safras.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getTalhao().getAreaAgricola().getEstado(),
                        Collectors.averagingDouble(Safra::getProdutividadeAno)))
                .entrySet().stream()
                .map(e -> new ProdutividadeMediaPorEstadoDto(
                        e.getKey(),
                        Math.round(e.getValue() * 100.0) / 100.0))
                .toList();
    }

    public List<ProdutividadeMediaPorTipoSoloDto> mediaPorTipoSolo() {
        List<Safra> safras = Optional.ofNullable(safraRepository.findAll()).orElse(List.of());

        return safras.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getTalhao().getTipoSolo().getTipoSolo(),
                        Collectors.averagingDouble(Safra::getProdutividadeAno)))
                .entrySet().stream()
                .map(e -> new ProdutividadeMediaPorTipoSoloDto(
                        e.getKey(),
                        Math.round(e.getValue() * 100.0) / 100.0))
                .toList();
    }

    public DuracaoDto calcularDuracao(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null)
            return null;
        Duration duracao = Duration.between(inicio, fim);
        long dias = duracao.toDays();
        long horas = duracao.toHours() % 24;
        long minutos = duracao.toMinutes() % 60;
        return new DuracaoDto(dias, horas, minutos);
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
    public List<SafraRelatorioDto> gerarRelatorioSafrasAprovadas() {
        List<Safra> safrasAprovadas = safraRepository.findByStatus(StatusSafra.Aprovado);

        return safrasAprovadas.stream().map(safra -> {
            DuracaoDto duracao = calcularDuracao(
                    safra.getDataCadastro(), safra.getDataAprovacao());

            return new SafraRelatorioDto(
                    safra.getId(),
                    safra.getUsuarioAnalista().getNome(),
                    safra.getDataCadastro(),
                    safra.getDataAtribuicao(),
                    safra.getDataAprovacao(),
                    duracao);
        }).toList();
    }

    @PreAuthorize("hasAuthority('Administrador') or hasAuthority('Consultor')")
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
                            mediaDuracao.toMinutes() % 60);

                    return new SafraRelatorioDto.MediaAnalistaDto(idAnalista, nomeAnalista, duracaoDto);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
