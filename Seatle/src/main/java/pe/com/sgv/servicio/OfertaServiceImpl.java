
package pe.com.sgv.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.sgv.model.Oferta;
import pe.com.sgv.dao.OfertaDao;

@Service
public class OfertaServiceImpl implements OfertaService{

    @Autowired
    private OfertaDao ofertaDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Oferta> listarOferta() {
        return (List<Oferta>) ofertaDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Oferta oferta) {
        ofertaDao.save(oferta);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Oferta encontrarOferta(Long oferta) {
        return ofertaDao.findById(oferta).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long oferta) {
        ofertaDao.deleteById(oferta);
    }

}
