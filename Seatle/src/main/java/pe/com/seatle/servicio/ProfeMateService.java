package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.ProfeMate;

public interface ProfeMateService {
    public List<ProfeMate> listarProfeMate();
    public void guardar(ProfeMate profeMate);
    public ProfeMate encontrarProfeMate (Long profeMate);
    public void eliminar(Long profeMate);
    
}
