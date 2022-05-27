
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.Tema;
import pe.com.seatle.dao.TemaDao;

@Service
public class TemaServiceImpl implements TemaService{

    @Autowired
    private TemaDao temaDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Tema> listarTema() {
        return (List<Tema>) temaDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Tema tema) {
        temaDao.save(tema);
    }

    @Override
    @Transactional(readOnly = true)
    public Tema encontrarTema(Long tema) {
        return temaDao.findById(tema).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long tema) {
        temaDao.deleteById(tema);
    }

}
