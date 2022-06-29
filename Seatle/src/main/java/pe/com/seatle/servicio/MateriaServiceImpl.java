
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.Materia;
import pe.com.seatle.dao.MateriaDao;

@Service
public class MateriaServiceImpl implements MateriaService{

    @Autowired
    private MateriaDao materiaDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Materia> listarMateria() {
        return (List<Materia>) materiaDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Materia materia) {
        materiaDao.save(materia);
    }

    @Override
    @Transactional(readOnly = true)
    public Materia encontrarMateria(Long materia) {
        return materiaDao.findById(materia).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long materia) {
        materiaDao.deleteById(materia);
    }

}
