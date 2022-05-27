
package pe.com.sgv.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity //Para persistencia de datos
@Table(name ="oferta")
//Esto se usa porque en la bd dice "oferta", pero en la clase java dice "Oferta"
public class Oferta implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idOferta;
    
    @NotEmpty
    private String detalle; 
    
    private Double precioOferta;
    
    @NotEmpty
    private String fechaInicio;
    
    @NotEmpty
    private String fechaFinal;
    
    @NotEmpty
    private String estado;
    
    @ManyToOne
    @JoinColumn(name="id_producto")
    private Producto idProducto;
    
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
