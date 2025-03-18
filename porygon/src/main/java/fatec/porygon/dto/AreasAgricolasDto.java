package fatec.porygon.dto;

import fatec.porygon.enums.StatusArea;
import lombok.Data;

@Data
public class AreasAgricolasDto {
    private Long id;
    private Long usuarioId;
    private Long usuarioUpgradeId;
    private Long usuarioAprovadorId;
    private String nomeFazenda;
    private String cultura;
    private Double produtividadeAno;
    private Double area;
    private String tipoSolo;
    private String cidade;
    private String estado;
    private String vetorRaiz;
    private String vetorAtualizado;
    private String vetorAprovado;
    private StatusArea status;
}