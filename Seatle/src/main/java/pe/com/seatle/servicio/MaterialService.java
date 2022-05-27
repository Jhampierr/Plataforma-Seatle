package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.Material;

public interface MaterialService {
    public List<Material> listarMaterial();
    public void guardar(Material material);
    public Material encontrarMaterial (Long material);
    public void eliminar(Long material);
    
}
