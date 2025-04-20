package co.ufps.pdau.repository;

import co.ufps.pdau.model.Denuncia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {
    List<Denuncia> findByCategorias_Id(Long categoriaId);
}
