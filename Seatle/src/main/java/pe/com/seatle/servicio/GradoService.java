package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.Grado;

public interface GradoService {
    public List<Grado> listarGrado();
    public void guardar(Grado grado);
    public Grado encontrarGrado (Long grado);
    public void eliminar(Long grado);
    
}
