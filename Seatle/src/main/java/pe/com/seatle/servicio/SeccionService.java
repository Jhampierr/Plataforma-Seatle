package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.Seccion;

public interface SeccionService {
    public List<Seccion> listarSeccion();
    public void guardar(Seccion seccion);
    public Seccion encontrarSeccion (Long seccion);
    public void eliminar(Long seccion);
    
}
