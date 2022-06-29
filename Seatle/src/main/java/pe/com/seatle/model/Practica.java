package pe.com.seatle.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name ="practica")
//Esto se usa porque en la bd dice "practica", pero en la clase java dice "Practica"
public class Practica implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idPractica;
            
    @ManyToOne
    @JoinColumn(name="id_materia")
    private Materia materia;
    
    @ManyToOne
    @JoinColumn(name="id_alumno")
    private Alumno alumno;
    
    @NotEmpty
    private String titulo;
    
    @NotEmpty
    private String clase;//Semana o sesion
    
    @Column(name="contenido_texto")
    private String contenidoTexto;//Explicacion de la practica
    
    @Column(name="contenido_archivo")
    private String contenidoArchivo;
    
    @Column(name="fecha_limite")
    private String fechaLimite;
    
    @Column(name="fecha_entrega")
    private String fechaDeEntrega;
    
    @Column(name="numero_pregunta")
    private String numeroPregunta;
    
    private String pregunta;
    private String respuesta;
    private String puntos;
            
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
