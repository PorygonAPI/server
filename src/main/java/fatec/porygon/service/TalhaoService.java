package fatec.porygon.service;

import fatec.porygon.entity.Cultura;
import fatec.porygon.entity.Talhao;
import fatec.porygon.entity.AreaAgricola;
import fatec.porygon.entity.TipoSolo;
import fatec.porygon.entity.Safra;
import fatec.porygon.dto.TalhaoDto;
import fatec.porygon.repository.TalhaoRepository;
import fatec.porygon.repository.AreaAgricolaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TalhaoService {

    @Autowired
    private TalhaoRepository talhaoRepository;

    @Autowired
    private CulturaService culturaService;

    @Autowired
    private TipoSoloService tipoSoloService;

    @Autowired
    private AreaAgricolaRepository areaAgricolaRepository;

    @Autowired
    private SafraService safraService;

    public Talhao criarTalhao(TalhaoDto dto) {
        Cultura cultura = culturaService.buscarOuCriar(dto.getCulturaNome());
        TipoSolo tipoSolo = tipoSoloService.buscarOuCriarById(dto.getTipoSoloId());
        AreaAgricola areaAgricola = areaAgricolaRepository.findById(dto.getAreaAgricolaId())
                .orElseThrow(() -> new RuntimeException("Área agrícola não encontrada."));

        Talhao talhao = new Talhao();
        talhao.setArea(dto.getArea());
        talhao.setTipoSolo(tipoSolo);
        talhao.setAreaAgricola(areaAgricola);

        talhao = talhaoRepository.save(talhao);

        Safra safra = new Safra();
        safra.setAno(dto.getAno());
        safra.setStatus(dto.getStatus());
        safra.setCultura(cultura);
        safra.setTalhao(talhao);

        safraService.criarSafra(safra);

        return talhao;
    }

    public List<Talhao> listarTodos() {
        return talhaoRepository.findAll();
    }

    public Talhao buscarPorId(Long id) {
        return talhaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Talhão não encontrado"));
    }

    public Talhao atualizarTalhao(Long id, TalhaoDto dto) {
        Talhao talhao = buscarPorId(id);

        talhao.setArea(dto.getArea());

        TipoSolo tipoSolo = tipoSoloService.buscarOuCriarById(dto.getTipoSoloId());
        talhao.setTipoSolo(tipoSolo);

        AreaAgricola areaAgricola = areaAgricolaRepository.findById(dto.getAreaAgricolaId())
                .orElseThrow(() -> new RuntimeException("Área agrícola não encontrada."));
        talhao.setAreaAgricola(areaAgricola);

        Safra safra = safraService.buscarPorTalhao(talhao.getId())
                .orElse(new Safra());

        safra.setAno(dto.getAno());
        safra.setStatus(dto.getStatus());

        Cultura cultura = culturaService.buscarOuCriar(dto.getCulturaNome());
        safra.setCultura(cultura);
        safra.setTalhao(talhao);

        safraService.criarSafra(safra);

        return talhaoRepository.save(talhao);
    }

    public void deletar(Long id) {
        Talhao talhao = buscarPorId(id);
        talhaoRepository.delete(talhao);
    }
}