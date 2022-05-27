
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.Producto;

@Repository
public interface ProductoDao extends CrudRepository<Producto, Long>{

    
}
