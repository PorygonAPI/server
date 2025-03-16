package fatec.porygon.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cargo_permissao")
public class CargoPermissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "permissao_id", nullable = false)
    private Permissao permissao;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Cargo getCargo() {return cargo;}
    public void setCargo(Cargo cargo) {this.cargo = cargo;}
    public Permissao getPermissao() {return permissao;}
    public void setPermissao(Permissao permissao) {this.permissao = permissao;}
}

