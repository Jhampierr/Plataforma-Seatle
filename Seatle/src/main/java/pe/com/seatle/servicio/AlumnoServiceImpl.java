
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.Alumno;
import pe.com.seatle.dao.AlumnoDao;

@Service
public class AlumnoServiceImpl implements AlumnoService{

    @Autowired
    private AlumnoDao alumnoDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Alumno> listarAlumno() {
        return (List<Alumno>) alumnoDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Alumno alumno) {
        alumnoDao.save(alumno);
    }

    @Override
    @Transactional(readOnly = true)
    public Alumno encontrarAlumno(Long alumno) {
        return alumnoDao.findById(alumno).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long alumno) {
        alumnoDao.deleteById(alumno);
    }

}
