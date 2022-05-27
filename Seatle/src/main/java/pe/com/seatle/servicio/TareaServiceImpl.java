
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.Tarea;
import pe.com.seatle.dao.TareaDao;

@Service
public class TareaServiceImpl implements TareaService{

    @Autowired
    private TareaDao tareaDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Tarea> listarTarea() {
        return (List<Tarea>) tareaDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Tarea tarea) {
        tareaDao.save(tarea);
    }

    @Override
    @Transactional(readOnly = true)
    public Tarea encontrarTarea(Long tarea) {
        return tareaDao.findById(tarea).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long tarea) {
        tareaDao.deleteById(tarea);
    }

}
