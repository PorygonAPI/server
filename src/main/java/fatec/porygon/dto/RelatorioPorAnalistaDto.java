package fatec.porygon.dto;

public class RelatorioPorAnalistaDto {
    private String nomeAnalista;
    private int quantidadePendentes;
    private int quantidadeAtribuidos;
    private int quantidadeAprovados;
    public String getNomeAnalista() {
        return nomeAnalista;
    }
    public void setNomeAnalista(String nomeAnalista) {
        this.nomeAnalista = nomeAnalista;
    }
    public int getQuantidadePendentes() {
        return quantidadePendentes;
    }
    public void setQuantidadePendentes(int quantidadePendentes) {
        this.quantidadePendentes = quantidadePendentes;
    }
    public int getQuantidadeAtribuidos() {
        return quantidadeAtribuidos;
    }
    public void setQuantidadeAtribuidos(int quantidadeAtribuidos) {
        this.quantidadeAtribuidos = quantidadeAtribuidos;
    }
    public int getQuantidadeAprovados() {
        return quantidadeAprovados;
    }
    public void setQuantidadeAprovados(int quantidadeAprovados) {
        this.quantidadeAprovados = quantidadeAprovados;
    }
    public RelatorioPorAnalistaDto(String nomeAnalista, int quantidadePendentes, int quantidadeAtribuidos,
            int quantidadeAprovados) {
        this.nomeAnalista = nomeAnalista;
        this.quantidadePendentes = quantidadePendentes;
        this.quantidadeAtribuidos = quantidadeAtribuidos;
        this.quantidadeAprovados = quantidadeAprovados;
    }
}
