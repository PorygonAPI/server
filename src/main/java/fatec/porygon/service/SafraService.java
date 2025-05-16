package fatec.porygon.service;

import fatec.porygon.dto.AtualizarSafraRequestDto;
import fatec.porygon.entity.*;
import fatec.porygon.enums.StatusSafra;
import fatec.porygon.repository.SafraRepository;
import fatec.porygon.repository.TalhaoRepository;
import fatec.porygon.repository.UsuarioRepository;
import fatec.porygon.utils.ConvertGeoJsonUtils;
import jakarta.persistence.EntityNotFoundException;
import fatec.porygon.dto.SafraDto;
import fatec.porygon.dto.TalhaoResumoDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                        TipoSoloService tipoSoloService
                        ) {
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
    public void atualizarSafra(String idSafra, AtualizarSafraRequestDto request) {
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

        safraRepository.save(safra);
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
    public List<Safra> associarAnalista(String talhaoId, Long usuarioId) {
        List<Safra> safras = safraRepository.findByTalhaoId(Long.valueOf(talhaoId));
        if (safras.isEmpty()) {
            throw new RuntimeException("Nenhuma safra encontrada para o talhão ID: " + talhaoId);
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        for (Safra safra : safras) {
            safra.setUsuarioAnalista(usuario);
            safra.setStatus(StatusSafra.Atribuido);
        }

        return safraRepository.saveAll(safras);
    }

    @Transactional
    public Safra criarSafra(Safra safra) {
        tratarArquivosDaninha(safra);
        return safraRepository.save(safra);
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
        safraRepository.buscarTalhoesBrutosPorStatus(idUsuario, StatusSafra.Aprovado)
    );

    List<TalhaoResumoDto> atribuidos = converterParaDto(
        safraRepository.buscarTalhoesBrutosPorStatus(idUsuario, StatusSafra.Atribuido)
    );

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
        Long safraId = Long.valueOf(linha[2].toString()); // pode ser String, então converte
        String cultura = (String) linha[3];

        resultado.add(new TalhaoResumoDto(talhaoId, nomeFazenda, safraId, cultura));
    }
    return resultado;
}


    private void tratarArquivosDaninha(Safra safra) {
        if (safra.getArquivoDaninha() != null && safra.getArquivoDaninha().toString().contains("{")) {
            safra.setArquivoDaninha(conversorGeoJson.convertGeoJsonToGeometry(safra.getArquivoDaninha().toString()));
        }
        if (safra.getArquivoFinalDaninha() != null && safra.getArquivoFinalDaninha().toString().contains("{")) {
            safra.setArquivoFinalDaninha(
                    conversorGeoJson.convertGeoJsonToGeometry(safra.getArquivoFinalDaninha().toString()));
        }
    }
}