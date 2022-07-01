
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.AlumMate;

@Repository
public interface AlumMateDao extends CrudRepository<AlumMate, Long>{

    
}
