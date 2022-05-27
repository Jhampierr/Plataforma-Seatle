package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.Pedido;

public interface PedidoService {
    public List<Pedido> listarPedido();
    public void guardar(Pedido pedido);
    public Pedido encontrarPedido (Long pedido);
    public void eliminar(Long pedido);      
}
