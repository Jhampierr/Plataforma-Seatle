package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.AulaVirtual;

public interface AulaVirtualService {
    public List<AulaVirtual> listarAulaVirtual();
    public void guardar(AulaVirtual aulaVirtual);
    public AulaVirtual encontrarAulaVirtual (Long aulaVirtual);
    public void eliminar(Long aulaVirtual);
    
}
