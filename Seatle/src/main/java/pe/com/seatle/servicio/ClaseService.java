package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.Clase;

public interface ClaseService {
    public List<Clase> listarClase();
    public void guardar(Clase clase);
    public Clase encontrarClase (Long clase);
    public void eliminar(Long clase);
    
}
