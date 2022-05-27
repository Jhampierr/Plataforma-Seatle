
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.Practica;
import pe.com.seatle.dao.PracticaDao;

@Service
public class PracticaServiceImpl implements PracticaService{

    @Autowired
    private PracticaDao practicaDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Practica> listarPractica() {
        return (List<Practica>) practicaDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Practica practica) {
        practicaDao.save(practica);
    }

    @Override
    @Transactional(readOnly = true)
    public Practica encontrarPractica(Long practica) {
        return practicaDao.findById(practica).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long practica) {
        practicaDao.deleteById(practica);
    }

}
