package fatec.porygon.dto;

import java.time.Duration;
import java.time.LocalDateTime;

public class SafraRelatorioDto {

    private String idSafra;
    private String nomeAnalista;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtribuicao;
    private LocalDateTime dataAprovacao;
    private DuracaoDto tempoTotal;
    

    public SafraRelatorioDto(String idSafra, String nomeAnalista,LocalDateTime dataCadastro,
                             LocalDateTime dataAtribuicao, LocalDateTime dataAprovacao, 
                             DuracaoDto tempoTotal) {
        this.idSafra = idSafra;
        this.nomeAnalista = nomeAnalista;
        this.dataCadastro = dataCadastro;
        this.dataAtribuicao = dataAtribuicao;
        this.dataAprovacao = dataAprovacao;
        this.tempoTotal = tempoTotal;;
    }

    public String getIdSafra() {return idSafra;}
    public void setIdSafra(String idSafra) {this.idSafra = idSafra;}

    public String getNomeAnalista() {return nomeAnalista;}
    public void setNomeAnalista(String nomeAnalista) {this.nomeAnalista = nomeAnalista;}

    public LocalDateTime getDataAtribuicao() {return dataAtribuicao;}
    public void setDataAtribuicao(LocalDateTime dataAtribuicao) {this.dataAtribuicao = dataAtribuicao;}

    public LocalDateTime getDataAprovacao() {return dataAprovacao;}
    public void setDataAprovacao(LocalDateTime dataAprovacao) {this.dataAprovacao = dataAprovacao;}

    public LocalDateTime getDataCadastro() {return dataCadastro;}
    public void setDataCadastro(LocalDateTime dataCadastro) {this.dataCadastro = dataCadastro;}

    public DuracaoDto getTempoTotal() {return tempoTotal;}
    public void setTempoTotal(DuracaoDto tempoTotal) {this.tempoTotal = tempoTotal;}

    public static class DuracaoDto {
    public long dias;
    public long horas;
    public long minutos;

    public DuracaoDto(long dias, long horas, long minutos) {
        this.dias = dias;
        this.horas = horas;
        this.minutos = minutos;
    }

    public long getDias() {return dias;}
    public void setDias(long dias) {this.dias = dias;}

    public long getHoras() {return horas;}
    public void setHoras(long horas) {this.horas = horas;}

    public long getMinutos() {return minutos;}
    public void setMinutos(long minutos) {this.minutos = minutos;}
    
    }

    public static class MediaAnalistaDto {
    private Long idAnalista;
    private String nomeAnalista;
    private DuracaoDto mediaDuracao;

    public MediaAnalistaDto(Long idAnalista, String nomeAnalista, DuracaoDto mediaDuracao) {
        this.idAnalista = idAnalista;
        this.nomeAnalista = nomeAnalista;
        this.mediaDuracao = mediaDuracao;
    }

    public Long getIdAnalista() {
        return idAnalista;
    }

    public void setIdAnalista(Long idAnalista) {
        this.idAnalista = idAnalista;
    }

    public String getNomeAnalista() {
        return nomeAnalista;
    }

    public void setNomeAnalista(String nomeAnalista) {
        this.nomeAnalista = nomeAnalista;
    }

    public DuracaoDto getMediaDuracao() {
        return mediaDuracao;
    }

    public void setMediaDuracao(DuracaoDto mediaDuracao) {
        this.mediaDuracao = mediaDuracao;
    }
}

}


