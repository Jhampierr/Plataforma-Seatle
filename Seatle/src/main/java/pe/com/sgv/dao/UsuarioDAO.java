
package pe.com.sgv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.sgv.model.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Long>{
    Usuario findByUsername(String username);
    
}
