
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.Curso;

@Repository
public interface CursoDao extends CrudRepository<Curso, Long>{

    
}
