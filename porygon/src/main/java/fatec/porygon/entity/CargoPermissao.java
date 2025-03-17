package fatec.porygon.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cargo_permissao")
public class CargoPermissao {

    @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo_id;

    @ManyToOne
    @JoinColumn(name = "permissao_id", nullable = false)
    private Permissao permissao_id;

    public Cargo getCargo_id() {return cargo_id;}
    public void setCargo_id(Cargo cargo_id) {this.cargo_id = cargo_id;}
    public Permissao getPermissao_id() {return permissao_id;}
    public void setPermissao_id(Permissao permissao_id) {this.permissao_id = permissao_id;}
}

