
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.Tema;

@Repository
public interface TemaDao extends CrudRepository<Tema, Long>{

    
}
