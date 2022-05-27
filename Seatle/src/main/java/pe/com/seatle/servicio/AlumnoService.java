package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.Alumno;

public interface AlumnoService {
    public List<Alumno> listarAlumno();
    public void guardar(Alumno alumno);
    public Alumno encontrarAlumno (Long alumno);
    public void eliminar(Long alumno);
                
}
