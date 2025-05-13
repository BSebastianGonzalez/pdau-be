package co.ufps.pdau.repository;

import co.ufps.pdau.model.Denuncia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {
    List<Denuncia> findByCategorias_Id(Long categoriaId);
    Optional<Denuncia> findByTokenSeguimiento(String token);
}
