package co.ufps.pdau.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchivoDenuncia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "denuncia_id")
    @JsonManagedReference
    private Denuncia denuncia;

    private String urlArchivo;
    private String nombreArchivo;
    private String tipoMime;
    private LocalDateTime fechaSubida;

}
