
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.AlumMate;
import pe.com.seatle.dao.AlumMateDao;

@Service
public class AlumMateServiceImpl implements AlumMateService{

    @Autowired
    private AlumMateDao alumMateDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<AlumMate> listarAlumMate() {
        return (List<AlumMate>) alumMateDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(AlumMate alumMate) {
        alumMateDao.save(alumMate);
    }

    @Override
    @Transactional(readOnly = true)
    public AlumMate encontrarAlumMate(Long alumMate) {
        return alumMateDao.findById(alumMate).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long alumMate) {
        alumMateDao.deleteById(alumMate);
    }

}
