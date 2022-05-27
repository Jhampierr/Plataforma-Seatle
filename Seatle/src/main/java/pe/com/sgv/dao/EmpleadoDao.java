
package pe.com.sgv.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.com.sgv.model.Empleado;

@Repository
public interface EmpleadoDao extends CrudRepository<Empleado, Long>{
    
}
