package fatec.porygon.service;

import fatec.porygon.dto.AtualizarSafraRequestDto;
import fatec.porygon.dto.SafraRelatorioDto;
import fatec.porygon.entity.*;
import fatec.porygon.enums.StatusSafra;
import fatec.porygon.repository.SafraRepository;
import fatec.porygon.repository.TalhaoRepository;
import fatec.porygon.repository.UsuarioRepository;
import fatec.porygon.utils.ConvertGeoJsonUtils;
import jakarta.persistence.EntityNotFoundException;
import fatec.porygon.dto.SafraDto;
import fatec.porygon.dto.SafraGeoJsonDto;
import fatec.porygon.dto.TalhaoPendenteDto;
import fatec.porygon.dto.TalhaoResumoDto;

import jakarta.persistence.EntityNotFoundException;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SafraService {

    private final SafraRepository safraRepository;
    private final UsuarioRepository usuarioRepository;
    private final ConvertGeoJsonUtils conversorGeoJson = new ConvertGeoJsonUtils();
    private final TalhaoRepository talhaoRepository;
    private final CulturaService culturaService;
    private final TipoSoloService tipoSoloService;


    @Autowired
    public SafraService(SafraRepository safraRepository,
            UsuarioRepository usuarioRepository,
            TalhaoRepository talhaoRepository,
            CulturaService culturaService,
            TipoSoloService tipoSoloService) {
        this.safraRepository = safraRepository;
        this.usuarioRepository = usuarioRepository;
        this.talhaoRepository = talhaoRepository;
        this.culturaService = culturaService;
        this.tipoSoloService = tipoSoloService;
    }

    @Transactional
    public Safra salvar(Safra safra) {
        tratarArquivosDaninha(safra);
        return safraRepository.save(safra);
    }

    public Safra buscarPorId(String id) {
        return safraRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Safra não encontrada com ID: " + id));
    }

    public List<SafraDto> listarTodas() {
        List<Safra> safras = safraRepository.findAll();
        return safras.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional
    public void atualizarSafra(String idSafra, AtualizarSafraRequestDto request, MultipartFile arquivoDaninha) {
        try {
            Safra safra = safraRepository.findById(idSafra)
                    .orElseThrow(() -> new RuntimeException("Safra não encontrada com ID: " + idSafra));

            if (request.getIdTalhao() != null) {
                Talhao novoTalhao = talhaoRepository.findById(request.getIdTalhao())
                        .orElseThrow(() -> new RuntimeException("Talhão não encontrado com ID: " + request.getIdTalhao()));
                safra.setTalhao(novoTalhao);
            }

            if(request.getArea() != null){
                safra.getTalhao().setArea(request.getArea());
            }

            if (request.getAnoSafra() != null) {
                safra.setAno(request.getAnoSafra());
            }

            if (request.getCulturaNome() != null) {
                Cultura cultura = culturaService.buscarOuCriar(request.getCulturaNome());
                safra.setCultura(cultura);
            }

            if (request.getProdutividadeAno() != null) {
                safra.setProdutividadeAno(request.getProdutividadeAno());
            }

            if (request.getTipoSoloNome() != null) {
                TipoSolo tipoSolo = tipoSoloService.buscarOuCriar(request.getTipoSoloNome());
                safra.getTalhao().setTipoSolo(tipoSolo);
            }

            if (arquivoDaninha != null && !arquivoDaninha.isEmpty()) {
                String conteudoGeoJson = new String(arquivoDaninha.getBytes(), StandardCharsets.UTF_8);
                Geometry geometria = conversorGeoJson.convertGeoJsonToGeometry(conteudoGeoJson);
                safra.setArquivoDaninha(geometria);
                safra.setDataUltimaVersao(LocalDateTime.now());
            }

            safraRepository.save(safra);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar arquivo de erva daninha", e);
        }
    }

    private SafraDto convertToDto(Safra safra) {
        SafraDto dto = new SafraDto();
        dto.setId(safra.getId());
        dto.setAno(safra.getAno());
        dto.setCultura(safra.getCultura() != null ? safra.getCultura().getNome() : null);
        dto.setStatus(safra.getStatus());
        dto.setTalhaoId(safra.getTalhao() != null ? safra.getTalhao().getId() : null);

        if (safra.getArquivoDaninha() != null) {
            dto.setArquivoDaninha(conversorGeoJson.convertGeometryToGeoJson(safra.getArquivoDaninha()));
        }
        if (safra.getArquivoFinalDaninha() != null) {
            dto.setArquivoFinalDaninha(conversorGeoJson.convertGeometryToGeoJson(safra.getArquivoFinalDaninha()));
        }

        return dto;
    }

    @Transactional
    public Safra atualizar(String id, Safra safraAtualizada) {
        Safra existente = buscarPorId(id);
        existente.setAno(safraAtualizada.getAno());
        existente.setCultura(safraAtualizada.getCultura());
        existente.setTalhao(safraAtualizada.getTalhao());
        existente.setStatus(safraAtualizada.getStatus());

        existente.setArquivoDaninha(safraAtualizada.getArquivoDaninha());
        existente.setArquivoFinalDaninha(safraAtualizada.getArquivoFinalDaninha());

        tratarArquivosDaninha(existente);

        return safraRepository.save(existente);
    }

    public void deletar(String id) {
        safraRepository.deleteById(id);
    }

    @Transactional
    public Safra associarAnalista(String safraId, Long usuarioId) {
        Safra safra = safraRepository.findById(safraId)
                .orElseThrow(() -> new RuntimeException("Safra não encontrada com ID: " + safraId));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + usuarioId));

            safra.setUsuarioAnalista(usuario);
            safra.setDataAtribuicao(LocalDateTime.now());
            safra.setStatus(StatusSafra.Atribuido);
        

        return safraRepository.save(safra);
    }

    @Query("SELECT MAX(CAST(s.id AS long)) FROM Safra s")
    private Long findLastId() {
        List<String> allIds = safraRepository.findAll().stream()
            .map(Safra::getId)
            .collect(Collectors.toList());
            
        return allIds.stream()
            .mapToLong(Long::parseLong)
            .max()
            .orElse(0L);
    }

    @Transactional
    public Safra criar(Safra safra, MultipartFile arquivoDaninha) {
        try {
            Long nextId = findLastId() + 1;
            safra.setId(nextId.toString());
            
            safra.setStatus(StatusSafra.Pendente);
            LocalDateTime now = LocalDateTime.now();
            safra.setDataCadastro(now);
            safra.setDataUltimaVersao(now);

            if (arquivoDaninha != null && !arquivoDaninha.isEmpty()) {
                String conteudoGeoJson = new String(arquivoDaninha.getBytes(), StandardCharsets.UTF_8);
                Geometry geometria = conversorGeoJson.convertGeoJsonToGeometry(conteudoGeoJson);
                safra.setArquivoDaninha(geometria);
            }

            if (safra.getProdutividadeAno() == null) {
                safra.setProdutividadeAno(0.0);
            }

            return safraRepository.save(safra);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar arquivo de erva daninha: " + e.getMessage(), e);
        }
    }

    public List<Safra> buscarPorTalhao(Long talhaoId) {
        List<Safra> safras = safraRepository.findByTalhaoId(talhaoId);
        if (safras.isEmpty()) {
            throw new RuntimeException("Nenhuma safra encontrada para o talhão ID: " + talhaoId);
        }
        return safras;
    }

    public Map<String, List<TalhaoResumoDto>> listarTalhoesPorUsuario(Long idUsuario) {
        List<TalhaoResumoDto> aprovados = converterParaDto(
                safraRepository.buscarTalhoesBrutosPorStatus(idUsuario, StatusSafra.Aprovado));

        List<TalhaoResumoDto> atribuidos = converterParaDto(
                safraRepository.buscarTalhoesBrutosPorStatus(idUsuario, StatusSafra.Atribuido));

        Map<String, List<TalhaoResumoDto>> resultado = new HashMap<>();
        resultado.put("aprovados", aprovados);
        resultado.put("atribuidos", atribuidos);
        return resultado;
    }

    private List<TalhaoResumoDto> converterParaDto(List<Object[]> dadosBrutos) {
        List<TalhaoResumoDto> resultado = new ArrayList<>();
        for (Object[] linha : dadosBrutos) {
            Long talhaoId = (Long) linha[0];
            String nomeFazenda = (String) linha[1];
            Long safraId = Long.valueOf(linha[2].toString());
            String cultura = (String) linha[3];
            Integer anoSafra = (Integer) linha[4];

            resultado.add(new TalhaoResumoDto(talhaoId, nomeFazenda, safraId, cultura, anoSafra));
        }
        return resultado;
    }

    private void tratarArquivosDaninha(Safra safra) {
    try {
        if (safra.getArquivoDaninha() instanceof MultipartFile) {
            MultipartFile arquivo = (MultipartFile) safra.getArquivoDaninha();
            if (!arquivo.isEmpty()) {
                String conteudoGeoJson = new String(arquivo.getBytes(), StandardCharsets.UTF_8);
                safra.setArquivoDaninha(conversorGeoJson.convertGeoJsonToGeometry(conteudoGeoJson));
            }
        }

        if (safra.getArquivoFinalDaninha() instanceof MultipartFile) {
            MultipartFile arquivoFinal = (MultipartFile) safra.getArquivoFinalDaninha();
            if (!arquivoFinal.isEmpty()) {
                String conteudoGeoJson = new String(arquivoFinal.getBytes(), StandardCharsets.UTF_8);
                safra.setArquivoFinalDaninha(conversorGeoJson.convertGeoJsonToGeometry(conteudoGeoJson));
            }
        }
    } catch (IOException e) {
        throw new RuntimeException("Erro ao processar arquivos de erva daninha", e);
    }
}


    public List<TalhaoPendenteDto> listarSafrasPendentes() {
        return talhaoRepository.findAll().stream()
                .flatMap(t -> t.getSafras().stream()
                        .filter(s -> s.getStatus() == StatusSafra.Pendente && s.getUsuarioAnalista() == null)
                        .map(safra -> new TalhaoPendenteDto(
                                safra.getId(),
                                t.getAreaAgricola().getNomeFazenda(),
                                safra.getCultura().getNome(),
                                safra.getProdutividadeAno(),
                                t.getArea(),
                                t.getTipoSolo().getTipoSolo(),
                                t.getAreaAgricola().getCidade().getNome(),
                                t.getAreaAgricola().getEstado())))
                .collect(Collectors.toList());
    }

    @Transactional
    public void salvarEdicaoSafra(String idSafra, MultipartFile geoJsonFile) throws IOException {
        Safra safra = safraRepository.findById(idSafra)
                .orElseThrow(() -> new EntityNotFoundException("Safra não encontrada"));

        if (safra.getUsuarioAnalista() == null || safra.getStatus() != StatusSafra.Atribuido) {
            throw new IllegalStateException("Safra não atribuída para edição");
        }

        if (geoJsonFile != null && !geoJsonFile.isEmpty()) {
            String conteudoGeoJson = new String(geoJsonFile.getBytes(), StandardCharsets.UTF_8);
            Geometry geometria = conversorGeoJson.convertGeoJsonToGeometry(conteudoGeoJson);
            safra.setArquivoDaninha(geometria);
        }

        safra.setDataUltimaVersao(LocalDateTime.now());
        safraRepository.save(safra);
    }

    @Transactional
    public void aprovarSafra(String idSafra, MultipartFile geoJsonFile) throws IOException {
        Safra safra = safraRepository.findById(idSafra)
                .orElseThrow(() -> new EntityNotFoundException("Safra não encontrada"));

        if (safra.getUsuarioAnalista() == null || safra.getStatus() != StatusSafra.Atribuido) {
            throw new IllegalStateException("Safra não atribuída para aprovação");
        }

        if (geoJsonFile != null && !geoJsonFile.isEmpty()) {
            String conteudoGeoJson = new String(geoJsonFile.getBytes(), StandardCharsets.UTF_8);
            Geometry geometria = conversorGeoJson.convertGeoJsonToGeometry(conteudoGeoJson);
            safra.setArquivoFinalDaninha(geometria);
        }

        safra.setStatus(StatusSafra.Aprovado);
        safra.setDataAprovacao(LocalDateTime.now());
        safra.setDataUltimaVersao(LocalDateTime.now());
        safraRepository.save(safra);
    }
 
     @Transactional
     public SafraGeoJsonDto buscarSafraGeoJson(String idSafra) {
         Safra safra = safraRepository.findById(idSafra)
                 .orElseThrow(() -> new RuntimeException("Safra não encontrada com ID: " + idSafra));
 
         AreaAgricola areaAgricola = safra.getTalhao().getAreaAgricola();
 
         SafraGeoJsonDto dto = new SafraGeoJsonDto();
         dto.setIdSafra(safra.getId());
         dto.setDataCadastro(safra.getDataCadastro());
         dto.setDataUltimaVersao(safra.getDataUltimaVersao());
 
         return dto;
     }
 
     public ByteArrayResource obterArquivoFazenda(Safra safra) {
         String geoJson = conversorGeoJson.convertGeometryToGeoJson(safra.getTalhao().getAreaAgricola().getArquivoFazenda());
         return criarArquivoGeoJson(geoJson, "arquivoFazenda.geojson");
     }
 
     public ByteArrayResource obterArquivoDaninha(Safra safra) {
         String geoJson = conversorGeoJson.convertGeometryToGeoJson(safra.getArquivoDaninha());
         return criarArquivoGeoJson(geoJson, "arquivoDaninha.geojson");
     }
 
     public ByteArrayResource obterArquivoFinalDaninha(Safra safra) {
         String geoJson = conversorGeoJson.convertGeometryToGeoJson(safra.getArquivoFinalDaninha());
         return criarArquivoGeoJson(geoJson, "arquivoFinalDaninha.geojson");
     }
 
     private ByteArrayResource criarArquivoGeoJson(String geoJson, String filename) {
        if (geoJson == null) {
           return null;
        }
         return new ByteArrayResource(geoJson.getBytes(StandardCharsets.UTF_8)) {
             @Override
             public String getFilename() {
                 return filename;
             }
         };
     }
 
     public Safra buscarSafra(String idSafra) {
         return safraRepository.findById(idSafra)
                 .orElseThrow(() -> new RuntimeException("Safra não encontrada com ID: " + idSafra));
     }

}