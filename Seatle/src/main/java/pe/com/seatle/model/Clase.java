package pe.com.seatle.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name ="clase")
//Esto se usa porque en la bd dice "clase", pero en la clase java dice "Clase"
public class Clase implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idClase;
    
    @ManyToOne
    @JoinColumn(name="id_profe_mate")
    private ProfeMate profeMate;
    
    @ManyToOne
    @JoinColumn(name="id_material")
    private Material material;
    
    @ManyToOne
    @JoinColumn(name="id_tarea")
    private Tarea tarea;
    
    @ManyToOne
    @JoinColumn(name="id_practica")
    private Practica practica;
    
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
