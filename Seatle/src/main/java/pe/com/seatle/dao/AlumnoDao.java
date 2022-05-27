
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.Alumno;

@Repository
public interface AlumnoDao extends CrudRepository<Alumno, Long>{

    
}
