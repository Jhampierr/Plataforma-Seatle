
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.Calificaciones;
import pe.com.seatle.dao.CalificacionesDao;

@Service
public class CalificacionesServiceImpl implements CalificacionesService{

    @Autowired
    private CalificacionesDao calificacionesDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Calificaciones> listarCalificaciones() {
        return (List<Calificaciones>) calificacionesDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Calificaciones calificaciones) {
        calificacionesDao.save(calificaciones);
    }

    @Override
    @Transactional(readOnly = true)
    public Calificaciones encontrarCalificaciones(Long calificaciones) {
        return calificacionesDao.findById(calificaciones).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long calificaciones) {
        calificacionesDao.deleteById(calificaciones);
    }

}
