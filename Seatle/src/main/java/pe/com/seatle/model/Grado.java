package pe.com.seatle.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name ="grado")
//Esto se usa porque en la bd dice "grado", pero en la clase java dice "Grado"
public class Grado implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idGrado;
    
    @NotEmpty
    private String descripcion;
    
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
