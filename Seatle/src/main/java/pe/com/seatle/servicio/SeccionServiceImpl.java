
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.Seccion;
import pe.com.seatle.dao.SeccionDao;

@Service
public class SeccionServiceImpl implements SeccionService{

    @Autowired
    private SeccionDao seccionDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Seccion> listarSeccion() {
        return (List<Seccion>) seccionDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Seccion seccion) {
        seccionDao.save(seccion);
    }

    @Override
    @Transactional(readOnly = true)
    public Seccion encontrarSeccion(Long seccion) {
        return seccionDao.findById(seccion).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long seccion) {
        seccionDao.deleteById(seccion);
    }

}
