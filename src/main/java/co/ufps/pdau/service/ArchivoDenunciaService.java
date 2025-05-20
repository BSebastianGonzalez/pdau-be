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
import java.util.Set;

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
        Denuncia denuncia = denunciaRepository.findById(denunciaId)
                .orElseThrow(() -> new IllegalArgumentException("Denuncia no encontrada con ID: " + denunciaId));

        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();

        // Tipos de archivo permitidos por Cloudinary
        Set<String> allowedImageTypes = Set.of(
                "image/jpeg", "image/jpg", "image/png", "image/gif", "image/bmp", "image/webp", "image/tiff", "image/svg+xml"
        );
        Set<String> allowedVideoTypes = Set.of(
                "video/mp4", "video/webm", "video/ogg", "video/ogv", "video/quicktime", "video/x-flv", "application/x-mpegURL", "video/3gpp", "audio/mpeg", "audio/mp3", "audio/wav"
        );
        Set<String> allowedRawTypes = Set.of(
                "application/pdf", "application/zip", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // docx
                "application/msword", // doc
                "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // xls, xlsx
                "text/plain", "application/json", "application/xml"
        );

        String resourceType;
        if (contentType == null) {
            throw new IllegalArgumentException("No se pudo determinar el tipo de archivo.");
        } else if (allowedImageTypes.contains(contentType)) {
            resourceType = "image";
        } else if (allowedVideoTypes.contains(contentType)) {
            resourceType = "video";
        } else if (allowedRawTypes.contains(contentType)) {
            resourceType = "raw";
        } else {
            throw new IllegalArgumentException("Tipo de archivo no permitido. Solo imágenes, videos o documentos comunes permitidos por Cloudinary.");
        }

        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.')); // incluye el punto
        }
        String baseName = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(0, originalFilename.lastIndexOf('.'))
                : originalFilename;
        baseName = baseName.replaceAll("[^a-zA-Z0-9_\\-]", "_");

        String publicId = baseName + extension;

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "resource_type", resourceType,
                "public_id", publicId
        ));

        ArchivoDenuncia ad = new ArchivoDenuncia();
        ad.setDenuncia(denuncia);
        ad.setUrlArchivo((String) uploadResult.get("secure_url"));
        ad.setNombreArchivo(originalFilename);
        ad.setTipoMime(contentType);
        ad.setFechaSubida(LocalDateTime.now());

        return archivoDenunciaRepository.save(ad);
    }

    public List<ArchivoDenuncia> obtenerArchivosPorDenuncia(Long denunciaId) {
        return archivoDenunciaRepository.findByDenunciaId(denunciaId);
    }
}
