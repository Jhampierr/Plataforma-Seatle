
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.Clase;
import pe.com.seatle.dao.ClaseDao;

@Service
public class ClaseServiceImpl implements ClaseService{

    @Autowired
    private ClaseDao claseDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Clase> listarClase() {
        return (List<Clase>) claseDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Clase clase) {
        claseDao.save(clase);
    }

    @Override
    @Transactional(readOnly = true)
    public Clase encontrarClase(Long clase) {
        return claseDao.findById(clase).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long clase) {
        claseDao.deleteById(clase);
    }

}
