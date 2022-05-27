
package pe.com.seatle.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.seatle.model.Reserva;
import pe.com.seatle.dao.ReservaDao;

@Service
public class ReservaServiceImpl implements ReservaService{

    @Autowired
    private ReservaDao reservaDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Reserva> listarReserva() {
        return (List<Reserva>) reservaDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Reserva reserva) {
        reservaDao.save(reserva);
    }

    @Override
    @Transactional(readOnly = true)
    public Reserva encontrarReserva(Long reserva) {
        return reservaDao.findById(reserva).orElse(null);
    }
    
    @Override
    @Transactional
    public void eliminar(Long reserva) {
        reservaDao.deleteById(reserva);
    }
}
