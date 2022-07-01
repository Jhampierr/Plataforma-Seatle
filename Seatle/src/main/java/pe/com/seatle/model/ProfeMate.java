package pe.com.seatle.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name ="profe_mate")
//Esto se usa porque en la bd dice "profeMate", pero en la clase java dice "ProfeMate"
public class ProfeMate implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idProfeMate;
    
    @ManyToOne
    @JoinColumn(name="id_materia")
    private Materia materia;
    
    @ManyToOne
    @JoinColumn(name="id_profesor")
    private Profesor profesor;
    
}
