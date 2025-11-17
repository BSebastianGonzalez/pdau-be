package co.ufps.pdau.repository;

import co.ufps.pdau.model.ComentarioDenuncia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioDenunciaRepository extends JpaRepository<ComentarioDenuncia, Long> {
    List<ComentarioDenuncia> findByDenunciaId(Long idDenuncia);
    List<ComentarioDenuncia> findByAdminId(Long adminId);
}
