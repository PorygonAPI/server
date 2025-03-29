package fatec.porygon.dto;

import java.time.LocalDateTime;

public class LogDto {

    private Long id;
    private Long usuario_id;
    private String acao;
    private LocalDateTime data_hora;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Long getUsuario_id() {return usuario_id;}
    public void setUsuario_id(Long usuario_id) {this.usuario_id = usuario_id;}
    public String getAcao() {return acao;}
    public void setAcao(String acao) {this.acao = acao;}
    public LocalDateTime getDat_hora() {return data_hora;}
    public void setData_hora(LocalDateTime data_hora) {this.data_hora = data_hora;}
}
