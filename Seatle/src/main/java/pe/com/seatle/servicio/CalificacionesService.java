package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.Calificaciones;

public interface CalificacionesService {
    public List<Calificaciones> listarCalificaciones();
    public void guardar(Calificaciones calificaciones);
    public Calificaciones encontrarCalificaciones (Long calificaciones);
    public void eliminar(Long calificaciones);
    
}
