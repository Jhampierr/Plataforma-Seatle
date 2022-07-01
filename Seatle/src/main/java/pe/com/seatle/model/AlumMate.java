package pe.com.seatle.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name ="alum_mate")
//Esto se usa porque en la bd dice "alumMate", pero en la clase java dice "AlumMate"
public class AlumMate implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idAlumfeMate;
    
    @ManyToOne
    @JoinColumn(name="id_materia")
    private Materia materia;
    
    @ManyToOne
    @JoinColumn(name="id_alumno")
    private Alumno alumno;
    
}
