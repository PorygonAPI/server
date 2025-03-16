package fatec.porygon.dto;

import java.time.LocalDateTime;

public class LogDTO {

    private Long id;
    private Long usuarioId;
    private String acao;
    private LocalDateTime dataHora;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Long getUsuarioId() {return usuarioId;}
    public void setUsuarioId(Long usuarioId) {this.usuarioId = usuarioId;}
    public String getAcao() {return acao;}
    public void setAcao(String acao) {this.acao = acao;}
    public LocalDateTime getDataHora() {return dataHora;}
    public void setDataHora(LocalDateTime dataHora) {this.dataHora = dataHora;}
}
