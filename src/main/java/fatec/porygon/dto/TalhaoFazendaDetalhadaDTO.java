package fatec.porygon.dto;

import fatec.porygon.entity.Safra;
import fatec.porygon.enums.StatusSafra;

import java.util.List;
import java.util.Set;

public class TalhaoFazendaDetalhadaDTO {
    private Long id;
    private Double area;
    private String tipoSolo;
    private List<SafraFazendaDetalhadaDTO> safras;

    public String getTipoSolo() {
        return tipoSolo;
    }

    public void setTipoSolo(String tipoSolo) {
        this.tipoSolo = tipoSolo;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public List<SafraFazendaDetalhadaDTO> getSafras() {
        return safras;
    }

    public void setSafras(List<SafraFazendaDetalhadaDTO> safras) {
        this.safras = safras;
    }
}