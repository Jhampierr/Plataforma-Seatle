
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.Clase;

@Repository
public interface ClaseDao extends CrudRepository<Clase, Long>{

    
}
