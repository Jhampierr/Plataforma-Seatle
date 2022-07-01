package pe.com.seatle.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name ="tarea")
//Esto se usa porque en la bd dice "tarea", pero en la clase java dice "Tarea"
public class Tarea implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idTarea;
            
    @ManyToOne
    @JoinColumn(name="id_tema")
    private Tema tema;
    
    @ManyToOne
    @JoinColumn(name="id_alumno")
    private Alumno alumno;
    
    @NotEmpty
    private String titulo;
    
    @NotEmpty
    private String clase;//Semana o sesion
    
    @Column(name="contenido_texto")
    private String contenidoTexto;//Explicacion de la tarea
    
    @Column(name="contenido_archivo")
    private String contenidoArchivo;
    
    @Column(name="fecha_limite")
    private String fechaLimite;
    
    @Column(name="fecha_entrega")
    private String fechaDeEntrega;
    private String entrega;
    private String puntos;
        
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
