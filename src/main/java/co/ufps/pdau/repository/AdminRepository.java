package co.ufps.pdau.repository;

import co.ufps.pdau.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByCorreo(String correo);
}
