
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
    @JoinColumn(name="curso_id_curso")
    private Curso curso;
    
    @ManyToOne
    @JoinColumn(name="curso_id_asistencia")
    private Asistencia asistencia;
        
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
