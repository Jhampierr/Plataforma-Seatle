
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.Profesor;
import pe.com.seatle.dao.ProfesorDao;

@Service
public class ProfesorServiceImpl implements ProfesorService{

    @Autowired
    private ProfesorDao profesorDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Profesor> listarProfesor() {
        return (List<Profesor>) profesorDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Profesor profesor) {
        profesorDao.save(profesor);
    }

    @Override
    @Transactional(readOnly = true)
    public Profesor encontrarProfesor(Long profesor) {
        return profesorDao.findById(profesor).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long profesor) {
        profesorDao.deleteById(profesor);
    }

}
