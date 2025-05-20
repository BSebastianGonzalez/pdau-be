package co.ufps.pdau.service;

import co.ufps.pdau.model.ArchivarDenuncia;
import co.ufps.pdau.repository.ArchivarDenunciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArchivarDenunciaService {

    @Autowired
    private final ArchivarDenunciaRepository archivarDenunciaRepository;

    public List<ArchivarDenuncia> getAllArchivarDenuncias() {
        return archivarDenunciaRepository.findAll();
    }

    public Optional<ArchivarDenuncia> getArchivarDenunciaById(Long id) {
        return archivarDenunciaRepository.findById(id);
    }

    public ArchivarDenuncia saveArchivarDenuncia(ArchivarDenuncia archivarDenuncia) {
        return archivarDenunciaRepository.save(archivarDenuncia);
    }

    public void deleteArchivarDenuncia(Long id) {
        archivarDenunciaRepository.deleteById(id);
    }
}