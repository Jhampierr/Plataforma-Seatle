package pe.com.seatle.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name ="tema")
//Esto se usa porque en la bd dice "tema", pero en la clase java dice "Tema"
public class Tema implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idTema;
    
    @NotEmpty
    private String nombre;
    
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
