package fatec.porygon.dto;

public class TalhaoResumoDto {
    private Long TalhaoId;
    private String nomeFazenda;
    private Long SafraId;
    private String cultura;

    
    public TalhaoResumoDto(Long talhaoId, String nomeFazenda, Long safraId, String cultura) {
        TalhaoId = talhaoId;
        this.nomeFazenda = nomeFazenda;
        SafraId = safraId;
        this.cultura = cultura;
    }
    
    public Long getTalhaoId() {
        return TalhaoId;
    }

    public void setTalhaoId(Long talhaoId) {
        TalhaoId = talhaoId;
    }

    public String getNomeFazenda() {
        return nomeFazenda;
    }

    public void setNomeFazenda(String nomeFazenda) {
        this.nomeFazenda = nomeFazenda;
    }

    public Long getSafraId() {
        return SafraId;
    }

    public void setSafraId(Long safraId) {
        SafraId = safraId;
    }

    public String getCultura() {
        return cultura;
    }

    public void setCultura(String cultura) {
        this.cultura = cultura;
    }

}
