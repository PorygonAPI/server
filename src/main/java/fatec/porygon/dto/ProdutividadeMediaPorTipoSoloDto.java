package fatec.porygon.dto;

public class ProdutividadeMediaPorTipoSoloDto {
    private String nomeTipoSolo;
    private double produtividadeMedia;

    public String getNomeTipoSolo() {
        return nomeTipoSolo;
    }

    public void setNomeTipoSolo(String nomeTipoSolo) {
        this.nomeTipoSolo = nomeTipoSolo;
    }

    public double getProdutividadeMedia() {
        return produtividadeMedia;
    }

    public void setProdutividadeMedia(double produtividadeMedia) {
        this.produtividadeMedia = produtividadeMedia;
    }

    public ProdutividadeMediaPorTipoSoloDto(String nomeTipoSolo, double produtividadeMedia) {
        this.nomeTipoSolo = nomeTipoSolo;
        this.produtividadeMedia = produtividadeMedia;
    }
}
