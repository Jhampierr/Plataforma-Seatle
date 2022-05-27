
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.seatle.model.Oferta;

@Repository
public interface OfertaDao extends CrudRepository<Oferta, Long>{

    
}
