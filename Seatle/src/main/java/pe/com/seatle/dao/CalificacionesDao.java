
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.Calificaciones;

@Repository
public interface CalificacionesDao extends CrudRepository<Calificaciones, Long>{

    
}
