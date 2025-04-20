package co.ufps.pdau.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carrera {

    @Id
    private Long id;
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_facultad", nullable = false)
    @JsonBackReference
    private Facultad facultad;
}
