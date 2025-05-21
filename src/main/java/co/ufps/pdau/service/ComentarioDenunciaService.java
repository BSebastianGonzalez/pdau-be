package co.ufps.pdau.service;

import co.ufps.pdau.model.ComentarioDenuncia;
import co.ufps.pdau.repository.ComentarioDenunciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComentarioDenunciaService {

    @Autowired
    private final ComentarioDenunciaRepository comentarioDenunciaRepository;

    public List<ComentarioDenuncia> getAllComentariosDenuncia() {
        return comentarioDenunciaRepository.findAll();
    }

    public Optional<ComentarioDenuncia> getComentarioDenunciaById(Long id) {
        return comentarioDenunciaRepository.findById(id);
    }

    public ComentarioDenuncia saveComentarioDenuncia(ComentarioDenuncia comentarioDenuncia) {
        return comentarioDenunciaRepository.save(comentarioDenuncia);
    }

    public void deleteComentarioDenuncia(Long id) {
        comentarioDenunciaRepository.deleteById(id);
    }

    public List<ComentarioDenuncia> getComentariosByDenunciaId(Long idDenuncia) {
        return comentarioDenunciaRepository.findByDenunciaId(idDenuncia);
    }
}