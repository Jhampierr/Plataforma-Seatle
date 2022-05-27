package pe.com.sgv.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name ="tipo_producto")
//Esto se usa porque en la bd dice "tipoProducto", pero en la clase java dice "TipoProducto"
public class TipoProducto implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idTipoProducto;
    
    @NotEmpty
    private String descripcion;  
    
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
