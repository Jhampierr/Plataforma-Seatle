
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.AulaVirtual;

@Repository
public interface AulaVirtualDao extends CrudRepository<AulaVirtual, Long>{

    
}
