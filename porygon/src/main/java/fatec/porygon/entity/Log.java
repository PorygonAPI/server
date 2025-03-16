package fatec.porygon.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario_id;

    @Column(nullable = false)
    private String acao;

    @Column(nullable = false)
    private LocalDateTime data_hora;
    
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Usuario getUsuario_id() {return usuario_id;}
    public void setUsuario_id(Usuario usuario_id) {this.usuario_id = usuario_id;}
    public String getAcao() {return acao;}
    public void setAcao(String acao) {this.acao = acao;}
    public LocalDateTime getData_hora() {return data_hora;}
    public void setData_hora(LocalDateTime data_hora) {this.data_hora = data_hora;}
}
