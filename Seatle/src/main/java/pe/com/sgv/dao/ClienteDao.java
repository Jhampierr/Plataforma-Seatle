
package pe.com.sgv.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.sgv.model.Cliente;

@Repository
public interface ClienteDao extends CrudRepository<Cliente, Long>{

    
}
