package pe.com.seatle.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name ="seccion")
//Esto se usa porque en la bd dice "seccion", pero en la clase java dice "Seccion"
public class Seccion implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idSeccion;
    
    @NotEmpty
    private String descripcion;
    
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
