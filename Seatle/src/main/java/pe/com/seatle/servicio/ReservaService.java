package pe.com.seatle.servicio;

import java.util.List;
import pe.com.seatle.model.Reserva;

public interface ReservaService {
    public List<Reserva> listarReserva();
    public void guardar(Reserva reserva);
    public Reserva encontrarReserva (Long reserva);
    public void eliminar(Long reserva);
            
}
