package co.ufps.pdau.service;

import co.ufps.pdau.model.Categoria;
import co.ufps.pdau.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    @Autowired
    private final CategoriaRepository categoriaRepository;

    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> getCategoriaById(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria createCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria updateCategoria(Long id, Categoria categoriaDetails) {
        return categoriaRepository.findById(id).map(categoria -> {
            categoria.setNombre(categoriaDetails.getNombre());
            categoria.setDescripcion(categoriaDetails.getDescripcion());
            return categoriaRepository.save(categoria);
        }).orElseThrow(() -> new RuntimeException("Categoria not found"));
    }

    public void deleteCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}

