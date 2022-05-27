
package pe.com.sgv.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity //Para persistencia de datos
@Table(name ="producto")
//Esto se usa porque en la bd dice "producto", pero en la clase java dice "Producto"
public class Producto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idProducto;
    
    @NotEmpty
    private String nombre;  
    
    private Double precio;
    
    @NotEmpty
    private String detalle; 
    
    //@Column(name="foto")
    private String imagen;
    
    @ManyToOne
    @JoinColumn(name="id_tipo_producto")
    private TipoProducto tipoProd;
    
    @ManyToOne
    @JoinColumn(name="id_cat_producto")
    private CatProducto catProd;
    
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
