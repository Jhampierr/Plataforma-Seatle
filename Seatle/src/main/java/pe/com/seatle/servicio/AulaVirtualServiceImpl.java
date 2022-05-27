
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.AulaVirtual;
import pe.com.seatle.dao.AulaVirtualDao;

@Service
public class AulaVirtualServiceImpl implements AulaVirtualService{

    @Autowired
    private AulaVirtualDao aulaVirtualDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<AulaVirtual> listarAulaVirtual() {
        return (List<AulaVirtual>) aulaVirtualDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(AulaVirtual aulaVirtual) {
        aulaVirtualDao.save(aulaVirtual);
    }

    @Override
    @Transactional(readOnly = true)
    public AulaVirtual encontrarAulaVirtual(Long aulaVirtual) {
        return aulaVirtualDao.findById(aulaVirtual).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long aulaVirtual) {
        aulaVirtualDao.deleteById(aulaVirtual);
    }

}
