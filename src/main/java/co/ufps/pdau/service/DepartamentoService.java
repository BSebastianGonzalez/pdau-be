package co.ufps.pdau.service;

import co.ufps.pdau.model.Departamento;
import co.ufps.pdau.repository.DepartamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartamentoService {

    @Autowired
    private final DepartamentoRepository departamentoRepository;

    public List<Departamento> getAllDepartamentos() {
        return departamentoRepository.findAll();
    }

    public Optional<Departamento> getDepartamentoById(Long id) {
        return departamentoRepository.findById(id);
    }

    public Departamento createDepartamento(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    public Departamento updateDepartamento(Long id, Departamento departamentoDetails) {
        return departamentoRepository.findById(id).map(departamento -> {
            departamento.setNombre(departamentoDetails.getNombre());
            departamento.setDescripcion(departamentoDetails.getDescripcion());
            return departamentoRepository.save(departamento);
        }).orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
    }

    public void deleteDepartamento(Long id) {
        departamentoRepository.deleteById(id);
    }
}