package pe.com.seatle.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name ="material")
//Esto se usa porque en la bd dice "material", pero en la clase java dice "Material"
public class Material implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idMaterial;
            
    @ManyToOne
    @JoinColumn(name="id_tema")
    private Tema tema;
    
    @NotEmpty
    private String titulo;
    
    @NotEmpty
    private String clase;//Semana o sesion
    
    @Column(name="contenido_texto")
    private String contenidoTexto;//Explicacion del material
    
    @Column(name="contenido_archivo")
    private String contenidoArchivo;
        
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
