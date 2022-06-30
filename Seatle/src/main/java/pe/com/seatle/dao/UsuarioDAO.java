
package pe.com.seatle.dao;

import org.springframework.data.repository.CrudRepository;
import pe.com.seatle.model.Usuario;

public interface UsuarioDAO extends CrudRepository<Usuario, Long>{
    Usuario findByUsername(String username);
    
}
