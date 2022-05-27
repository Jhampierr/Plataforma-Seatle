
package pe.com.sgv.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.sgv.model.Oferta;

@Repository
public interface OfertaDao extends CrudRepository<Oferta, Long>{

    
}
