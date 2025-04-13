package main.java.fatec.porygon.service;

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
        Cultura cultura = culturaService.buscarOuCriar(dto.getCultura_nome());
        TipoSolo tipoSolo = tipoSoloService.buscarOuCriarById(dto.getTipo_solo_id());
        AreaAgricola area = areaAgricolaRepository.findById(dto.getArea_agricola_id())
                .orElseThrow(() -> new RuntimeException("Área agrícola não encontrada."));

        Talhao talhao = new Talhao();
        talhao.setArea(dto.getArea());
        talhao.setProdutividadeAno(dto.getProdutividade_ano());
        talhao.setTipoSolo(tipoSolo);
        talhao.setAreaAgricola(area);
        talhao.setArquivoDaninha(dto.getArquivo_daninha());
        talhao.setArquivoFinalDaninha(dto.getArquivo_final_daninha());

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
        talhao.setProdutividadeAno(dto.getProdutividade_ano());
        talhao.setArquivoDaninha(dto.getArquivo_daninha());
        talhao.setArquivoFinalDaninha(dto.getArquivo_final_daninha());

        TipoSolo tipoSolo = tipoSoloService.buscarOuCriarById(dto.getTipo_solo_id());
        talhao.setTipoSolo(tipoSolo);

        return talhaoRepository.save(talhao);
    }

    public void deletar(Long id) {
        Talhao talhao = buscarPorId(id);
        talhaoRepository.delete(talhao);
    }
}

