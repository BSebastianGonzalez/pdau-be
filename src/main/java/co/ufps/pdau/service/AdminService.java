package co.ufps.pdau.service;

import co.ufps.pdau.model.Admin;
import co.ufps.pdau.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Autowired
    private final AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(Long id, Admin adminDetails) {
        return adminRepository.findById(id).map(admin -> {
            admin.setCedula(adminDetails.getCedula());
            admin.setNombre(adminDetails.getNombre());
            admin.setApellido(adminDetails.getApellido());
            admin.setTelefono(adminDetails.getTelefono());
            admin.setDireccion(adminDetails.getDireccion());
            admin.setCorreo(adminDetails.getCorreo());
            admin.setContrasenia(adminDetails.getContrasenia());
            return adminRepository.save(admin);
        }).orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}
