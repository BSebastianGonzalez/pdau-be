package co.ufps.pdau.service;

import co.ufps.pdau.model.CambioEstado;
import co.ufps.pdau.repository.CambioEstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CambioEstadoService {

    @Autowired
    private final CambioEstadoRepository cambioEstadoRepository;

    public List<CambioEstado> getAllCambiosEstado() {
        return cambioEstadoRepository.findAll();
    }

    public Optional<CambioEstado> getCambioEstadoById(Long id) {
        return cambioEstadoRepository.findById(id);
    }

    public CambioEstado saveCambioEstado(CambioEstado cambioEstado) {
        return cambioEstadoRepository.save(cambioEstado);
    }

    public void deleteCambioEstado(Long id) {
        cambioEstadoRepository.deleteById(id);
    }

    public List<CambioEstado> getCambiosEstadoByDenunciaId(Long idDenuncia) {
        return cambioEstadoRepository.findByDenunciaId(idDenuncia);
    }
}