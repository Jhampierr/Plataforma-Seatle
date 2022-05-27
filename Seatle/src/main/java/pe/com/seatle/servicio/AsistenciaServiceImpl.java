
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.Asistencia;
import pe.com.seatle.dao.AsistenciaDao;

@Service
public class AsistenciaServiceImpl implements AsistenciaService{

    @Autowired
    private AsistenciaDao asistenciaDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Asistencia> listarAsistencia() {
        return (List<Asistencia>) asistenciaDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Asistencia asistencia) {
        asistenciaDao.save(asistencia);
    }

    @Override
    @Transactional(readOnly = true)
    public Asistencia encontrarAsistencia(Long asistencia) {
        return asistenciaDao.findById(asistencia).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long asistencia) {
        asistenciaDao.deleteById(asistencia);
    }

}
