package fatec.porygon.dto;

import java.util.List;

public class RelatorioProdutividade {

    private List<ProdutividadeMediaPorCulturaDto> produtividadePorCultura;
    private List<ProdutividadeMediaPorEstadoDto> produtividadePorEstado;
    private List<ProdutividadeMediaPorTipoSoloDto> produtividadePorTipoSolo;
    private CulturaMaisProdutivaDto culturaMaisProdutiva;
    private List<RankingEstadosDto> rankingTop5Estados;

    public RelatorioProdutividade(
        List<ProdutividadeMediaPorCulturaDto> produtividadePorCultura,
        List<ProdutividadeMediaPorEstadoDto> produtividadePorEstado,
        List<ProdutividadeMediaPorTipoSoloDto> produtividadePorTipoSolo,
        CulturaMaisProdutivaDto culturaMaisProdutiva,
        List<RankingEstadosDto> rankingTop5Estados
    ) {
        this.produtividadePorCultura = produtividadePorCultura;
        this.produtividadePorEstado = produtividadePorEstado;
        this.produtividadePorTipoSolo = produtividadePorTipoSolo;
        this.culturaMaisProdutiva = culturaMaisProdutiva;
        this.rankingTop5Estados = rankingTop5Estados;
    }

    public List<ProdutividadeMediaPorCulturaDto> getProdutividadePorCultura() {
        return produtividadePorCultura;
    }

    public List<ProdutividadeMediaPorEstadoDto> getProdutividadePorEstado() {
        return produtividadePorEstado;
    }

    public List<ProdutividadeMediaPorTipoSoloDto> getProdutividadePorTipoSolo() {
        return produtividadePorTipoSolo;
    }

    public CulturaMaisProdutivaDto getCulturaMaisProdutiva() {
        return culturaMaisProdutiva;
    }

    public List<RankingEstadosDto> getRankingTop5Estados() {
        return rankingTop5Estados;
    }
}
