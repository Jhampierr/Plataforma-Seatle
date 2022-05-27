
package pe.com.sgv.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity //Para persistencia de datos
@Table(name ="pedido")
//Esto se usa porque en la bd dice "pedido", pero en la clase java dice "Pedido"
public class Pedido implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idPedido;
        
    private Integer numero;
    
    @NotEmpty
    private String fecha;
    
    @NotEmpty
    private String hora;
    
    @Column(name="direcion_pedido")
    private String direccionPedido;
    
    @Column(name="cantidad_producto")
    private Integer cantidadProducto;
    
    
    @Column(name="cantidad_oferta")
    private Integer cantidadOferta;
    
    private Double total;
    
    @NotEmpty
    private String estado;
    
    @NotEmpty
    private String detalle;
    
    @ManyToOne
    @JoinColumn(name="cliente_id_cliente")
    private Cliente cliente;
        
    @ManyToOne
    @JoinColumn(name="empleado_id_empleado")
    private Empleado empleado;
    
    @ManyToOne
    @JoinColumn(name="producto_id_producto")
    private Producto producto;
    
    @ManyToOne
    @JoinColumn(name="oferta_id_oferta")
    private Oferta oferta;
    
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
