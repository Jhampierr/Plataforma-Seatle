package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.Materia;

public interface MateriaService {
    public List<Materia> listarMateria();
    public void guardar(Materia materia);
    public Materia encontrarMateria (Long materia);
    public void eliminar(Long materia);
    
}
