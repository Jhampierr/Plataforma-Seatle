
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.ProfeMate;
import pe.com.seatle.dao.ProfeMateDao;

@Service
public class ProfeMateServiceImpl implements ProfeMateService{

    @Autowired
    private ProfeMateDao profeMateDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<ProfeMate> listarProfeMate() {
        return (List<ProfeMate>) profeMateDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(ProfeMate profeMate) {
        profeMateDao.save(profeMate);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfeMate encontrarProfeMate(Long profeMate) {
        return profeMateDao.findById(profeMate).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long profeMate) {
        profeMateDao.deleteById(profeMate);
    }

}
