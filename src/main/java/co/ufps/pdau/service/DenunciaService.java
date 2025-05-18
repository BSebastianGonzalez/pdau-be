package co.ufps.pdau.service;

import co.ufps.pdau.model.Denuncia;
import co.ufps.pdau.model.Departamento;
import co.ufps.pdau.repository.DenunciaRepository;
import co.ufps.pdau.repository.DepartamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DenunciaService {
    @Autowired
    private final DenunciaRepository denunciaRepository;

    @Autowired
    private final DepartamentoRepository departamentoRepository;

    public List<Denuncia> getAllDenuncias() {
        return denunciaRepository.findAll();
    }

    public Optional<Denuncia> getDenunciaById(Long id) {
        return denunciaRepository.findById(id);
    }

    public Denuncia createDenuncia(Denuncia denuncia) {
        denuncia.setFechaCreacion(new Date());
        String tokenSeguimiento = UUID.randomUUID().toString();
        denuncia.setTokenSeguimiento(tokenSeguimiento);
        return denunciaRepository.save(denuncia);
    }

    public Denuncia updateDenuncia(Long id, Denuncia denunciaDetails) {
        return denunciaRepository.findById(id).map(denuncia -> {
            denuncia.setTitulo(denunciaDetails.getTitulo());
            denuncia.setDescripcion(denunciaDetails.getDescripcion());
            denuncia.setFechaCreacion(denunciaDetails.getFechaCreacion());
            denuncia.setTokenSeguimiento(denunciaDetails.getTokenSeguimiento());
            denuncia.setArchivado(denunciaDetails.isArchivado());
            denuncia.setFechaArchivado(denunciaDetails.getFechaArchivado());
            denuncia.setCategorias(denunciaDetails.getCategorias());
            denuncia.setEstado(denunciaDetails.getEstado());
            denuncia.setDepartamento(denunciaDetails.getDepartamento());
            return denunciaRepository.save(denuncia);
        }).orElseThrow(() -> new RuntimeException("Denuncia not found"));
    }

    public void deleteDenuncia(Long id) {
        denunciaRepository.deleteById(id);
    }

    public List<Denuncia> getDenunciasByCategoria(Long idCategoria) {
        return denunciaRepository.findByCategorias_Id(idCategoria);
    }

    public String getTokenByDenunciaId(Long id) {
        return denunciaRepository.findById(id)
                .map(Denuncia::getTokenSeguimiento)
                .orElseThrow(() -> new RuntimeException("Denuncia no encontrada"));
    }

    public Optional<Denuncia> getDenunciaByToken(String token) {
        return denunciaRepository.findByTokenSeguimiento(token);
    }

    public List<Denuncia> getDenunciasArchivadas() {
        return denunciaRepository.findByArchivado(true);
    }

    public List<Denuncia> getDenunciasNoArchivadas() {
        return denunciaRepository.findByArchivado(false);
    }

    public Denuncia toggleArchivado(Long id) {
        return denunciaRepository.findById(id).map(denuncia -> {
            denuncia.setArchivado(!denuncia.isArchivado());
            return denunciaRepository.save(denuncia);
        }).orElseThrow(() -> new RuntimeException("Denuncia no encontrada"));
    }

    public Denuncia asignarDenunciaADepartamento(Long idDenuncia, Long idDepartamento) {
        return denunciaRepository.findById(idDenuncia).map(denuncia -> {
            Departamento departamento = departamentoRepository.findById(idDepartamento)
                    .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
            denuncia.setDepartamento(departamento);
            return denunciaRepository.save(denuncia);
        }).orElseThrow(() -> new RuntimeException("Denuncia no encontrada"));
    }

    public List<Denuncia> getDenunciasByDepartamento(Long idDepartamento) {
        return denunciaRepository.findByDepartamento_Id(idDepartamento);
    }
}

