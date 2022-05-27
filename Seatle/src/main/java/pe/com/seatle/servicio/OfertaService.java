package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.Oferta;

public interface OfertaService {
    public List<Oferta> listarOferta();
    public void guardar(Oferta oferta);
    public Oferta encontrarOferta (Long oferta);
    public void eliminar(Long oferta);
    
}
