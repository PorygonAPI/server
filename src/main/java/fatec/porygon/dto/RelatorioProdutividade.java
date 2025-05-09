import java.util.List;

public class RelatorioProdutividade {

    private List<ProdutividadeMediaPorCulturaDTO> produtividadePorCultura;
    private List<ProdutividadeMediaPorEstadoDTO> produtividadePorEstado;
    private List<ProdutividadeMediaPorTipoSoloDTO> produtividadePorTipoSolo;
    private CulturaMaisProdutivaDTO culturaMaisProdutiva;
    private List<RankingEstadosDTO> rankingTop5Estados;

    public RelatorioProdutividade(
        List<ProdutividadeMediaPorCulturaDTO> produtividadePorCultura,
        List<ProdutividadeMediaPorEstadoDTO> produtividadePorEstado,
        List<ProdutividadeMediaPorTipoSoloDTO> produtividadePorTipoSolo,
        CulturaMaisProdutivaDTO culturaMaisProdutiva,
        List<RankingEstadosDTO> rankingTop5Estados
    ) {
        this.produtividadePorCultura = produtividadePorCultura;
        this.produtividadePorEstado = produtividadePorEstado;
        this.produtividadePorTipoSolo = produtividadePorTipoSolo;
        this.culturaMaisProdutiva = culturaMaisProdutiva;
        this.rankingTop5Estados = rankingTop5Estados;
    }

    public List<ProdutividadeMediaPorCulturaDTO> getProdutividadePorCultura() {
        return produtividadePorCultura;
    }

    public List<ProdutividadeMediaPorEstadoDTO> getProdutividadePorEstado() {
        return produtividadePorEstado;
    }

    public List<ProdutividadeMediaPorTipoSoloDTO> getProdutividadePorTipoSolo() {
        return produtividadePorTipoSolo;
    }

    public CulturaMaisProdutivaDTO getCulturaMaisProdutiva() {
        return culturaMaisProdutiva;
    }

    public List<RankingEstadosDTO> getRankingTop5Estados() {
        return rankingTop5Estados;
    }
}
