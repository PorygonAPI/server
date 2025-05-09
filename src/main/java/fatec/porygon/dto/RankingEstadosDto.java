public class RankingEstadosDto {
    private String nomeEstado;
    private double produtividadeMedia;
    public String getNomeEstado() {
        return nomeEstado;
    }
    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }
    public double getProdutividadeMedia() {
        return produtividadeMedia;
    }
    public void setProdutividadeMedia(double produtividadeMedia) {
        this.produtividadeMedia = produtividadeMedia;
    }
    public RankingEstadosDto(String nomeEstado, double produtividadeMedia) {
        this.nomeEstado = nomeEstado;
        this.produtividadeMedia = produtividadeMedia;
    } 
}
