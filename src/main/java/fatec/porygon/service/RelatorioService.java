package fatec.porygon.service;

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

@Service
public class RelatorioService {

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
}

