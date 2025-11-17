package co.ufps.pdau.repository;

import co.ufps.pdau.model.ArchivarDenuncia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArchivarDenunciaRepository extends JpaRepository<ArchivarDenuncia, Long> {
    List<ArchivarDenuncia> findByAdminId(Long adminId);
}
