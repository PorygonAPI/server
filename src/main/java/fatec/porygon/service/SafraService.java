package fatec.porygon.service;

import fatec.porygon.entity.Safra;
import fatec.porygon.entity.Usuario;
import fatec.porygon.enums.StatusSafra;
import fatec.porygon.repository.SafraRepository;
import fatec.porygon.repository.UsuarioRepository;
import fatec.porygon.utils.ConvertGeoJsonUtils;
import fatec.porygon.dto.SafraDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SafraService {

    private final SafraRepository safraRepository;
    private final UsuarioRepository usuarioRepository;
    private final ConvertGeoJsonUtils conversorGeoJson = new ConvertGeoJsonUtils();

    @Autowired
    public SafraService(SafraRepository safraRepository, UsuarioRepository usuarioRepository) {
        this.safraRepository = safraRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Safra salvar(Safra safra) {
        tratarArquivosDaninha(safra);
        return safraRepository.save(safra);
    }

    public Safra buscarPorId(Long id) {
        return safraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Safra não encontrada"));
    }

    public List<SafraDto> listarTodas() {
        List<Safra> safras = safraRepository.findAll();
        return safras.stream()
                .map(this::convertToDto)
                .toList();
    }

    private SafraDto convertToDto(Safra safra) {
        SafraDto dto = new SafraDto();
        dto.setId(safra.getId());
        dto.setAno(safra.getAno());
        dto.setCultura(safra.getCultura() != null ? safra.getCultura().getNome() : null);
        dto.setStatus(safra.getStatus());
        dto.setTalhaoId(safra.getTalhao() != null ? safra.getTalhao().getId() : null);

        // >>> AQUI >>> ao invés de usar .toText() (que gera WKT Polygon), convertemos
        // para GeoJSON:
        if (safra.getArquivoDaninha() != null) {
            dto.setArquivoDaninha(conversorGeoJson.convertGeometryToGeoJson(safra.getArquivoDaninha()));
        }
        if (safra.getArquivoFinalDaninha() != null) {
            dto.setArquivoFinalDaninha(conversorGeoJson.convertGeometryToGeoJson(safra.getArquivoFinalDaninha()));
        }

        return dto;
    }

    @Transactional
    public Safra atualizar(Long id, Safra safraAtualizada) {
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

    public void deletar(Long id) {
        safraRepository.deleteById(id);
    }

    @Transactional
    public Safra associarAnalista(Long safraId, Long usuarioId) {
        Safra safra = buscarPorId(safraId);
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        safra.setUsuarioAnalista(usuario);
        safra.setStatus(StatusSafra.Atribuido);
        return safraRepository.save(safra);
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

    public Map<String, List<TalhaoResumoDTO>> listarTalhoesPorUsuario(Long idUsuario) {
        List<TalhaoResumoDTO> aprovados = safraRepository.buscarTalhoesPorStatus(idUsuario, StatusSafra.Aprovado);
        List<TalhaoResumoDTO> atribuidos = safraRepository.buscarTalhoesPorStatus(idUsuario, StatusSafra.Atribuido);

        Map<String, List<TalhaoResumoDTO>> resultado = new HashMap<>();
        resultado.put("aprovados", aprovados);
        resultado.put("atribuidos", atribuidos);
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