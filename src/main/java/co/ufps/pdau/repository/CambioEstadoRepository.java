package co.ufps.pdau.repository;

import co.ufps.pdau.model.CambioEstado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CambioEstadoRepository extends JpaRepository<CambioEstado, Long> {
    List<CambioEstado> findByDenunciaId(Long idDenuncia);
}
