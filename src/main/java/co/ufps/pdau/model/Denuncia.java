package co.ufps.pdau.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;
    private Date fechaCreacion;
    private String tokenSeguimiento;

    @ManyToOne
    @JoinColumn(name = "id_facultad")
    private Facultad facultad;

    @ManyToOne
    @JoinColumn(name = "id_carrera")
    @JsonBackReference
    private Carrera carrera;

    @ManyToMany
    @JoinTable(
            name = "denuncia_categoria",
            joinColumns = @JoinColumn(name = "id_denuncia"),
            inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    @JsonBackReference
    private List<Categoria> categorias = new ArrayList<>();
}
