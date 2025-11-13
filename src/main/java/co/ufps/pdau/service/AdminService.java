package co.ufps.pdau.service;

import co.ufps.pdau.DTO.LoginResponse;
import co.ufps.pdau.model.Admin;
import co.ufps.pdau.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Autowired
    private final AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    public Admin createAdmin(Admin admin) {
        admin.setContrasenia(passwordEncoder.encode(admin.getContrasenia()));
        return adminRepository.save(admin);
    }

    public Admin updateUser(Long id, Admin adminDetails) {
        return adminRepository.findById(id).map(admin -> {
            admin.setCedula(adminDetails.getCedula());
            admin.setNombre(adminDetails.getNombre());
            admin.setApellido(adminDetails.getApellido());
            admin.setTelefono(adminDetails.getTelefono());
            admin.setDireccion(adminDetails.getDireccion());
            admin.setCorreo(adminDetails.getCorreo());
            admin.setContrasenia(adminDetails.getContrasenia());
            admin.setRole(adminDetails.getRole());
            return adminRepository.save(admin);
        }).orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    public LoginResponse login(String correo, String contrasenia) {
        Optional<Admin> adminOptional = adminRepository.findByCorreo(correo);

        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            if (admin.getContrasenia().equals(contrasenia)) {
                return new LoginResponse(true, "Inicio de sesión exitoso", admin);
            } else {
                return new LoginResponse(false, "Contraseña incorrecta", null);
            }
        } else {
            return new LoginResponse(false, "Correo no registrado", null);
        }
    }
}
