
package pe.com.seatle.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity //Para persistencia de datos
@Table(name ="aula_virtual")
//Esto se usa porque en la bd dice "aula_virtual", pero en la clase java dice "AulaVirtual"
public class AulaVirtual implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idAulaVirtual;
        
    @ManyToOne
    @JoinColumn(name="id_curso")
    private Clase curso;
    
    @ManyToOne
    @JoinColumn(name="id_asistencia")
    private Asistencia asistencia;
    
    @ManyToOne
    @JoinColumn(name="id_calificaciones")
    private Calificaciones calificaciones;
    
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
