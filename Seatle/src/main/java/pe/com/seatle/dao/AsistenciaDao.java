
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.Asistencia;

@Repository
public interface AsistenciaDao extends CrudRepository<Asistencia, Long>{
    
}
