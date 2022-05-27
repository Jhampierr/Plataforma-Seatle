package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.Tarea;

public interface TareaService {
    public List<Tarea> listarTarea();
    public void guardar(Tarea tarea);
    public Tarea encontrarTarea (Long tarea);
    public void eliminar(Long tarea);
    
}
