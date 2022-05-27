package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.Grado;

public interface CatProductoService {
    public List<Grado> listarCatProducto();
    public void guardar(Grado catProducto);
    public Grado encontrarCatProducto (Long catProducto);
    public void eliminar(Long catProducto);
                
}
