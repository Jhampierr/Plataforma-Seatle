
package pe.com.sgv.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.sgv.model.Producto;

@Repository
public interface ProductoDao extends CrudRepository<Producto, Long>{

    
}
