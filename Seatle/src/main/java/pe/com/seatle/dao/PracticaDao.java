
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.Practica;

@Repository
public interface PracticaDao extends CrudRepository<Practica, Long>{

    
}
