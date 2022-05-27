package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.Curso;

public interface CursoService {
    public List<Curso> listarCurso();
    public void guardar(Curso curso);
    public Curso encontrarCurso (Long curso);
    public void eliminar(Long curso);
    
}
