package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.Tema;

public interface TemaService {
    public List<Tema> listarTema();
    public void guardar(Tema tema);
    public Tema encontrarTema (Long tema);
    public void eliminar(Long tema);
    
}
