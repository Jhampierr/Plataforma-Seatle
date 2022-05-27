
package pe.com.seatle.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.Pedido;

@Repository
public interface PedidoDao extends CrudRepository<Pedido, Long>{
    
}
