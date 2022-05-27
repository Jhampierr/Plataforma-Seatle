
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.Profesor;

@Repository
public interface ProfesorDao extends CrudRepository<Profesor, Long>{

    
}
