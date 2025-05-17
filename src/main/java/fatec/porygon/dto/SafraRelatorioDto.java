package fatec.porygon.dto;

import java.time.LocalDateTime;

public class SafraRelatorioDto {

    private String idSafra;
    private String nomeAnalista;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtribuicao;
    private LocalDateTime dataAprovacao;
    private String tempoTotalAnalise;
    

    public SafraRelatorioDto(String idSafra, String nomeAnalista,LocalDateTime dataCadastro,
                             LocalDateTime dataAtribuicao, LocalDateTime dataAprovacao, 
                             String tempoTotalAnalise) {
        this.idSafra = idSafra;
        this.nomeAnalista = nomeAnalista;
        this.dataCadastro = dataCadastro;
        this.dataAtribuicao = dataAtribuicao;
        this.dataAprovacao = dataAprovacao;
        this.tempoTotalAnalise = tempoTotalAnalise;
    }

    public String getIdSafra() {return idSafra;}
    public void setIdSafra(String idSafra) {this.idSafra = idSafra;}

    public String getNomeAnalista() {return nomeAnalista;}
    public void setNomeAnalista(String nomeAnalista) {this.nomeAnalista = nomeAnalista;}

    public LocalDateTime getDataAtribuicao() {return dataAtribuicao;}
    public void setDataAtribuicao(LocalDateTime dataAtribuicao) {this.dataAtribuicao = dataAtribuicao;}

    public LocalDateTime getDataAprovacao() {return dataAprovacao;}
    public void setDataAprovacao(LocalDateTime dataAprovacao) {this.dataAprovacao = dataAprovacao;}

    public String getTempoTotalAnalise() {return tempoTotalAnalise;}
    public void setTempoTotalAnalise(String tempoTotalAnalise) {this.tempoTotalAnalise = tempoTotalAnalise;}

    public LocalDateTime getDataCadastro() {return dataCadastro;}
    public void setDataCadastro(LocalDateTime dataCadastro) {this.dataCadastro = dataCadastro;}
}

