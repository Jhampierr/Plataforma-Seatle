package pe.com.sgv.servicio;

import java.util.List;
import pe.com.sgv.model.Oferta;

public interface OfertaService {
    public List<Oferta> listarOferta();
    public void guardar(Oferta oferta);
    public Oferta encontrarOferta (Long oferta);
    public void eliminar(Long oferta);
    
}
