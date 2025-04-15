package fatec.porygon.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cargo_permissao")
public class CargoPermissao {

    @EmbeddedId
    private CargoPermissaoId id;

    @ManyToOne
    @MapsId("cargoId")
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    @ManyToOne
    @MapsId("permissaoId")
    @JoinColumn(name = "permissao_id", nullable = false)
    private Permissao permissao;

    public CargoPermissao() {}

    public CargoPermissao(Cargo cargo, Permissao permissao) {
        this.id = new CargoPermissaoId(cargo.getId(), permissao.getId());
        this.cargo = cargo;
        this.permissao = permissao;
    }

    public CargoPermissaoId getId() {
        return id;
    }

    public void setId(CargoPermissaoId id) {
        this.id = id;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Permissao getPermissao() {
        return permissao;
    }

    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }
}
