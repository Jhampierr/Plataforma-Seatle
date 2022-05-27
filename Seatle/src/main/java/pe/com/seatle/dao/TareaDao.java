
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.Tarea;

@Repository
public interface TareaDao extends CrudRepository<Tarea, Long>{

    
}
