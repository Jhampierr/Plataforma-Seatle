package pe.com.seatle.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name ="asistencia")
//Esto se usa porque en la bd dice "asistencia", pero en la clase java dice "Asistencia"
public class Asistencia implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idAsistencia;
        
    @ManyToOne
    @JoinColumn(name="alumno_id_alumno")
    private Alumno alumno;
    
    @NotEmpty
    private String fecha;
    
    @NotEmpty
    private String estado;
    
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
