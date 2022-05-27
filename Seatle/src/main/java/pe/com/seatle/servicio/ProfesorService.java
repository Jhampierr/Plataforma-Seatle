package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.Profesor;

public interface ProfesorService {
    public List<Profesor> listarProfesor();
    public void guardar(Profesor profesor);
    public Profesor encontrarProfesor (Long profesor);
    public void eliminar(Long profesor);
    
}
