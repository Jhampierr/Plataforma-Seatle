
package pe.com.sgv.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.sgv.model.Pedido;
import pe.com.sgv.dao.PedidoDao;

@Service
public class PedidoServiceImpl implements PedidoService{

    @Autowired
    private PedidoDao pedidoDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Pedido> listarPedido() {
        
        return (List<Pedido>) pedidoDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Pedido pedido) {
        
        pedidoDao.save(pedido);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Pedido encontrarPedido(Long pedido) {
        return pedidoDao.findById(pedido).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long pedido) {
        pedidoDao.deleteById(pedido);
    }
    
}
