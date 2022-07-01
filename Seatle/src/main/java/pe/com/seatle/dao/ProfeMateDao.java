
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.ProfeMate;

@Repository
public interface ProfeMateDao extends CrudRepository<ProfeMate, Long>{

    
}
