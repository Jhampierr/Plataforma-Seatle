package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.Practica;

public interface PracticaService {
    public List<Practica> listarPractica();
    public void guardar(Practica practica);
    public Practica encontrarPractica (Long practica);
    public void eliminar(Long practica);
    
}
