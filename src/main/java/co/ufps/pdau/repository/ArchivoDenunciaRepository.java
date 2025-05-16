package co.ufps.pdau.repository;

import co.ufps.pdau.model.ArchivoDenuncia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArchivoDenunciaRepository extends JpaRepository<ArchivoDenuncia, Long> {
    List<ArchivoDenuncia> findByDenunciaId(Long denunciaId);
}
