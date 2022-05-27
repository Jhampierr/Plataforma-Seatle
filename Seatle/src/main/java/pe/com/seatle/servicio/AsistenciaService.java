package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.Asistencia;

public interface AsistenciaService {
    public List<Asistencia> listarAsistencia();
    public void guardar(Asistencia asistencia);
    public Asistencia encontrarAsistencia (Long asistencia);
    public void eliminar(Long asistencia);
    
}
