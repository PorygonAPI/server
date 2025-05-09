package fatec.porygon.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import fatec.porygon.dto.CulturaMaisProdutivaDto;
import fatec.porygon.dto.ProdutividadeMediaPorCulturaDto;
import fatec.porygon.dto.ProdutividadeMediaPorEstadoDto;
import fatec.porygon.dto.ProdutividadeMediaPorTipoSoloDto;
import fatec.porygon.dto.RankingEstadosDto;
import fatec.porygon.dto.RelatorioPorAnalistaDto;
import fatec.porygon.dto.RelatorioProdutividadeDto;
import fatec.porygon.dto.StatusRelatorioDto;
import fatec.porygon.entity.Safra;
import fatec.porygon.enums.StatusSafra;
import fatec.porygon.repository.SafraRepository;

@Service
public class RelatorioService {

    private final SafraRepository safraRepository;

    public RelatorioService(SafraRepository safraRepository) {
        this.safraRepository = safraRepository;
    }

    public StatusRelatorioDto getContagemPorStatus(LocalDate dataInicial, LocalDate dataFinal) {
        List<Safra> safras;
    
        if (dataInicial != null && dataFinal != null) {
            safras = safraRepository.findAll().stream()
                .filter(s -> {
                    LocalDate dataCadastro = s.getDataCadastro().toLocalDate();
                    return ( !dataCadastro.isBefore(dataInicial) && !dataCadastro.isAfter(dataFinal) );
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
}

