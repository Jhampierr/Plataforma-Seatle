package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.AlumMate;

public interface AlumMateService {
    public List<AlumMate> listarAlumMate();
    public void guardar(AlumMate alumMate);
    public AlumMate encontrarAlumMate (Long alumMate);
    public void eliminar(Long alumMate);
    
}
