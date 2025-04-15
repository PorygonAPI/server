package fatec.porygon.dto;

public class CargoPermissaoDto {

    private Long cargoId;
    private Long permissaoId;

    public CargoPermissaoDto() {}

    public CargoPermissaoDto(Long cargoId, Long permissaoId) {
        this.cargoId = cargoId;
        this.permissaoId = permissaoId;
    }

    public Long getCargoId() {
        return cargoId;
    }

    public void setCargoId(Long cargoId) {
        this.cargoId = cargoId;
    }

    public Long getPermissaoId() {
        return permissaoId;
    }

    public void setPermissaoId(Long permissaoId) {
        this.permissaoId = permissaoId;
    }
}