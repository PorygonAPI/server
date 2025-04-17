package fatec.porygon.dto;

import java.util.List;
import java.util.Set;

public class FazendaDetalhadaDto {
    private AreaAgricolaSimplificadaDto fazenda;
    private Set<TalhaoFazendaDetalhadaDTO> talhao;

    public AreaAgricolaSimplificadaDto getFazenda() {
        return fazenda;
    }

    public void setFazenda(AreaAgricolaSimplificadaDto fazenda) {
        this.fazenda = fazenda;
    }

    public Set<TalhaoFazendaDetalhadaDTO> getTalhao() {
        return talhao;
    }

    public void setTalhao(Set<TalhaoFazendaDetalhadaDTO> talhao) {
        this.talhao = talhao;
    }
}
