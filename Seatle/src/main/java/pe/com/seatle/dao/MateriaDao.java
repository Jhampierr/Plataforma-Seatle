
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.Materia;

@Repository
public interface MateriaDao extends CrudRepository<Materia, Long>{
    
}
