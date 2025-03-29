package fatec.porygon.dto;

public class CargoPermissaoDto {

    private Long cargo_id;
    private Long permissao_id;

    public CargoPermissaoDto() {}

    public CargoPermissaoDto(Long cargo_id, Long permissao_id) {
        this.cargo_id = cargo_id;
        this.permissao_id = permissao_id;
    }

    public Long getPermissao_id() { return permissao_id; }
    public void setPermissao_id(Long permissao_id) { this.permissao_id = permissao_id; }

    public Long getCargo_id() { return cargo_id; }
    public void setCargo_id(Long cargo_id) { this.cargo_id = cargo_id; }
}