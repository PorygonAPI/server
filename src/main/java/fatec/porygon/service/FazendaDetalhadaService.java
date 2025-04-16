package fatec.porygon.service;

import fatec.porygon.dto.*;
import fatec.porygon.entity.AreaAgricola;
import fatec.porygon.entity.Talhao;
import fatec.porygon.repository.AreaAgricolaRepository;
import fatec.porygon.repository.TalhaoRepository;
import fatec.porygon.utils.ConvertGeoJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FazendaDetalhadaService {

    private final AreaAgricolaRepository areaAgricolaRepository;
    private final TalhaoRepository talhaoRepository;
    private final ConvertGeoJsonUtils conversorGeoJson = new ConvertGeoJsonUtils();


    @Autowired
    public FazendaDetalhadaService(AreaAgricolaRepository areaAgricolaRepository, TalhaoRepository talhaoRepository) {
        this.areaAgricolaRepository = areaAgricolaRepository;
        this.talhaoRepository = talhaoRepository;
    }

    public Optional<FazendaDetalhadaDto> getFazendaDetalhadaById(Long fazendaId) {
        Optional<AreaAgricola> areaAgricolaOpt = areaAgricolaRepository.findById(fazendaId);

        if (areaAgricolaOpt.isEmpty()) {
            return Optional.empty();
        }

        AreaAgricola areaAgricola = areaAgricolaOpt.get();

        AreaAgricolaSimplificadaDto areaAgricolaDto = new AreaAgricolaSimplificadaDto(
                areaAgricola.getNomeFazenda(),
                areaAgricola.getCidade().getNome(),
                areaAgricola.getEstado(),
                areaAgricola.getStatus(),
                conversorGeoJson.convertGeometryToGeoJson(areaAgricola.getArquivoFazenda())
        );



        Set<TalhaoFazendaDetalhadaDTO> talhaoDtos = talhaoRepository.findByAreaAgricolaId(fazendaId).stream()
                .map(talhao -> {
                    TalhaoFazendaDetalhadaDTO dto = new TalhaoFazendaDetalhadaDTO();
                    dto.setId(talhao.getId());
                    dto.setTipoSolo(talhao.getTipoSolo().getTipo());
                    dto.setArea(talhao.getArea());
                    List<SafraFazendaDetalhadaDTO> safraDtos = talhao.getSafras().stream()
                            .map(safra -> {
                                SafraFazendaDetalhadaDTO safraDto = new SafraFazendaDetalhadaDTO();
                                safraDto.setId(safra.getId());
                                safraDto.setAno(safra.getAno());
                                safraDto.setProdutividadeAno(safra.getProdutividadeAno());
                                safraDto.setStatus(safra.getStatus());
                                safraDto.setCultura(safra.getCultura().getNome());
                                safraDto.setArquivoDaninha(conversorGeoJson.convertGeometryToGeoJson(safra.getArquivoDaninha()));
                                safraDto.setArquivoFinalDaninha(conversorGeoJson.convertGeometryToGeoJson(safra.getArquivoFinalDaninha()));
                                return safraDto;
                            })
                            .collect(Collectors.toList());

                    dto.setSafras(safraDtos);
                    return dto;
                })
                .collect(Collectors.toSet());

        FazendaDetalhadaDto fazendaDetalhadaDto = new FazendaDetalhadaDto();
        fazendaDetalhadaDto.setFazenda(areaAgricolaDto);
        fazendaDetalhadaDto.setTalhao(talhaoDtos);
        return Optional.of(fazendaDetalhadaDto);

    }

}
