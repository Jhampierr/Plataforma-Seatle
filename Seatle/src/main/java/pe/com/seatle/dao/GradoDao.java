
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.Grado;

@Repository
public interface GradoDao extends CrudRepository<Grado, Long>{

}
