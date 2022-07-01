package pe.com.seatle.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name ="calificaciones")
//Esto se usa porque en la bd dice "calificaciones", pero en la clase java dice "Calificaciones"
public class Calificaciones implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idCalificaciones;
    
    @ManyToOne
    @JoinColumn(name="id_clase")
    private Clase clase;
    
    @ManyToOne
    @JoinColumn(name="id_alum_mate")
    private AlumMate alumMate;
    
    private String descripcion;
    
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
