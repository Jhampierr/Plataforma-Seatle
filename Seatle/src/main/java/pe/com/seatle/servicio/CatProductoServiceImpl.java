
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.Grado;
import pe.com.seatle.dao.CatProductoDao;

@Service
public class CatProductoServiceImpl implements CatProductoService{

    @Autowired
    private CatProductoDao catProductoDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Grado> listarCatProducto() {
        return (List<Grado>) catProductoDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Grado catProducto) {
        catProductoDao.save(catProducto);
    }

    @Override
    @Transactional(readOnly = true)
    public Grado encontrarCatProducto(Long catProducto) {
        return catProductoDao.findById(catProducto).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long catProducto) {
        catProductoDao.deleteById(catProducto);
    }

}
