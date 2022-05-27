
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.Material;

@Repository
public interface MaterialDao extends CrudRepository<Material, Long>{
    
}
