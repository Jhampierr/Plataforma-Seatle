
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.Grado;
import pe.com.seatle.dao.GradoDao;

@Service
public class GradoServiceImpl implements GradoService{

    @Autowired
    private GradoDao gradoDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Grado> listarGrado() {
        return (List<Grado>) gradoDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Grado grado) {
        gradoDao.save(grado);
    }

    @Override
    @Transactional(readOnly = true)
    public Grado encontrarGrado(Long grado) {
        return gradoDao.findById(grado).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long grado) {
        gradoDao.deleteById(grado);
    }

}
