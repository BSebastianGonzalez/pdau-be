package co.ufps.pdau.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private boolean archivado;
    private Date fechaArchivado;

    @ManyToMany
    @JoinTable(
            name = "denuncia_categoria",
            joinColumns = @JoinColumn(name = "id_denuncia"),
            inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    @JsonManagedReference
    private List<Categoria> categorias = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "estado_id")
    @JsonManagedReference
    private Estado estado;

    @OneToMany(mappedBy = "denuncia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<ArchivoDenuncia> archivosDenuncia = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "departamento_id")
    @JsonManagedReference
    private Departamento departamento;
}
