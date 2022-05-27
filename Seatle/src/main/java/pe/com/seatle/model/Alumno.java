package pe.com.seatle.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name ="alumno")
//Esto se usa porque en la bd dice "alumno", pero en la clase java dice "Alumno"
public class Alumno implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idAlumno;
        
    @NotEmpty
    private String nombre;
    
    @NotEmpty
    private String apellido;
    
    @NotEmpty
    private String telefono;
    
    @NotEmpty
    private String correo;
    
    @ManyToOne
    @JoinColumn(name="grado_id_grado")
    private Grado grado;
    
    @ManyToOne
    @JoinColumn(name="seccion_id_seccion")
    private Seccion seccion;
    
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
