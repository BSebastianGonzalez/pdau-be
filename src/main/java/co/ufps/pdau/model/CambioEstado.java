package co.ufps.pdau.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CambioEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "denuncia_id")
    private Denuncia denuncia;

    @ManyToOne
    @JoinColumn(name = "administrador_id")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "estado_anterior_id")
    private Estado estadoAnterior;

    @ManyToOne
    @JoinColumn(name = "estado_nuevo_id")
    private Estado estado;

    private String justificacion;
    private Date fechaCambio;
}
