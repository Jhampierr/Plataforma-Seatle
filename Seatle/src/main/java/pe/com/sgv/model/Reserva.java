package pe.com.sgv.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity //Para persistencia de datos
@Table(name = "reserva")
//Esto se usa porque en la bd dice "reserva", pero en la clase java dice "Reserva"
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Indica cual es el campo de la llave primaria de nuestra tabla en la BD
    private Long idReserva;
    
    private String mesa;
    
    private Integer cantidad;
    
    @NotEmpty
    private String fecha;
    
    @NotEmpty
    private String hora;
    
    @ManyToOne
    @JoinColumn(name="cliente_id_cliente")
    private Cliente cliente;
    
    private String fechaUpdate;
    private String hostName;
    private String ip;
}
