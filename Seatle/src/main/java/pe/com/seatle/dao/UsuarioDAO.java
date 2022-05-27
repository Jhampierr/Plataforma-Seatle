
package pe.com.seatle.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.seatle.model.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Long>{
    Usuario findByUsername(String username);
    
}
