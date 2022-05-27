
package pe.com.sgv.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.sgv.model.TipoEmpleado;
import pe.com.sgv.dao.TipoEmpleadoDao;

@Service
public class TipoEmpleadoServiceImpl implements TipoEmpleadoService{

    @Autowired
    private TipoEmpleadoDao tipoEmpleadoDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<TipoEmpleado> listarTipoEmpleado() {
        return (List<TipoEmpleado>) tipoEmpleadoDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(TipoEmpleado tipoEmpleado) {
        tipoEmpleadoDao.save(tipoEmpleado);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoEmpleado encontrarTipoEmpleado(Long tipoEmpleado) {
        return tipoEmpleadoDao.findById(tipoEmpleado).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long tipoEmpleado) {
        tipoEmpleadoDao.deleteById(tipoEmpleado);
    }
}
