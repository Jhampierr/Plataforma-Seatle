
package pe.com.sgv.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.sgv.model.Reserva;

@Repository
public interface ReservaDao extends CrudRepository<Reserva, Long>{

    
}
