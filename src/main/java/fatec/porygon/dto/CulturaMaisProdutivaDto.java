package fatec.porygon.dto;

public class CulturaMaisProdutivaDto {
    private String nomeCultura;
    private double produtividadeMedia;
    public String getNomeCultura() {
        return nomeCultura;
    }
    public void setNomeCultura(String nomeCultura) {
        this.nomeCultura = nomeCultura;
    }
    public double getProdutividadeMedia() {
        return produtividadeMedia;
    }
    public void setProdutividadeMedia(double produtividadeMedia) {
        this.produtividadeMedia = produtividadeMedia;
    }
    public CulturaMaisProdutivaDto(String nomeCultura, double produtividadeMedia) {
        this.nomeCultura = nomeCultura;
        this.produtividadeMedia = produtividadeMedia;
    }
    
}
