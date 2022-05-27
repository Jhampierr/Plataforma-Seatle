
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.Seccion;

@Repository
public interface SeccionDao extends CrudRepository<Seccion, Long>{

    
}
