package pe.com.sgv.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name ="cat_producto")
//Esto se usa porque en la bd dice "cat_producto", pero en la clase java dice "CatProducto"
public class CatProducto implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idCatProducto;
    
    @NotEmpty
    private String descripcion;  
    
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
