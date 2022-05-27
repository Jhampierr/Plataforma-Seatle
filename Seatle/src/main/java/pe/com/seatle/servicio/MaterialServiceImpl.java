
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.Material;
import pe.com.seatle.dao.MaterialDao;

@Service
public class MaterialServiceImpl implements MaterialService{

    @Autowired
    private MaterialDao materialDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Material> listarMaterial() {
        return (List<Material>) materialDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Material material) {
        materialDao.save(material);
    }

    @Override
    @Transactional(readOnly = true)
    public Material encontrarMaterial(Long material) {
        return materialDao.findById(material).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long material) {
        materialDao.deleteById(material);
    }

}
