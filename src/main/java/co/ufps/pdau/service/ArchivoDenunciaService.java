package co.ufps.pdau.service;

import co.ufps.pdau.model.ArchivoDenuncia;
import co.ufps.pdau.model.Denuncia;
import co.ufps.pdau.repository.ArchivoDenunciaRepository;
import co.ufps.pdau.repository.DenunciaRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArchivoDenunciaService {
    @Autowired
    private final Cloudinary cloudinary;

    @Autowired
    private final ArchivoDenunciaRepository archivoDenunciaRepository;
    @Autowired
    private final DenunciaRepository denunciaRepository;

    public List<ArchivoDenuncia> obtenerTodosLosArchivos() {
        return archivoDenunciaRepository.findAll();
    }

    public ArchivoDenuncia uploadFile(MultipartFile file, Long denunciaId) throws IOException {
        Denuncia denuncia = denunciaRepository.findById(denunciaId).
                orElseThrow(() -> new IllegalArgumentException("Denuncia no encontrada con ID: " + denunciaId));

        String originalFilename = file.getOriginalFilename();

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "resource_type", "auto",
                "public_id", "evidencias/" + originalFilename
        ));

        ArchivoDenuncia ad = new ArchivoDenuncia();
        ad.setDenuncia(denuncia);
        ad.setUrlArchivo((String) uploadResult.get("secure_url"));
        ad.setNombreArchivo(file.getOriginalFilename());
        ad.setTipoMime(file.getContentType());
        ad.setFechaSubida(LocalDateTime.now());

        return archivoDenunciaRepository.save(ad);
    }

    public List<ArchivoDenuncia> obtenerArchivosPorDenuncia(Long denunciaId) {
        return archivoDenunciaRepository.findByDenunciaId(denunciaId);
    }
}
