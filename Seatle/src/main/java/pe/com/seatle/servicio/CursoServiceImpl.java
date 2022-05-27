
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.Curso;
import pe.com.seatle.dao.CursoDao;

@Service
public class CursoServiceImpl implements CursoService{

    @Autowired
    private CursoDao cursoDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Curso> listarCurso() {
        return (List<Curso>) cursoDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Curso curso) {
        cursoDao.save(curso);
    }

    @Override
    @Transactional(readOnly = true)
    public Curso encontrarCurso(Long curso) {
        return cursoDao.findById(curso).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long curso) {
        cursoDao.deleteById(curso);
    }

}
