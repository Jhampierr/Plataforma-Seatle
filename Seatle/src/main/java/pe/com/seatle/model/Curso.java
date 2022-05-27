package pe.com.seatle.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name ="curso")
//Esto se usa porque en la bd dice "curso", pero en la clase java dice "Curso"
public class Curso implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idCurso;
    
    @NotEmpty
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name="grado_id_grado")
    private Grado grado;
    
    @ManyToOne
    @JoinColumn(name="tema_id_tema")
    private Tema tema;
    
    @ManyToOne
    @JoinColumn(name="material_id_material")
    private Material material;
    
    @ManyToOne
    @JoinColumn(name="tarea_id_tarea")
    private Tarea tarea;
    
    @ManyToOne
    @JoinColumn(name="practica_id_practica")
    private Practica practica;
    
    private String descripcion;
    
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
