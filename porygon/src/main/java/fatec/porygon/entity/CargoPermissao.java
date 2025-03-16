package fatec.porygon.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cargo_permissao")
public class CargoPermissao {

    @ManyToOne
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargoID;

    @ManyToOne
    @JoinColumn(name = "permissao_id", nullable = false)
    private Permissao permissaoID;

    public Cargo getCargoID() {return cargoID;}
    public void setCargoID(Cargo cargoID) {this.cargoID = cargoID;}
    public Permissao getPermissaoID() {return permissaoID;}
    public void setPermissaoID(Permissao permissaoID) {this.permissaoID = permissaoID;}
}

