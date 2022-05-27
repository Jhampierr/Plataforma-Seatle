package pe.com.sgv.servicio;

import java.util.List;
import pe.com.sgv.model.TipoEmpleado;

public interface TipoEmpleadoService {
    public List<TipoEmpleado> listarTipoEmpleado();
    public void guardar(TipoEmpleado tipoEmpleado);
    public TipoEmpleado encontrarTipoEmpleado (Long tipoEmpleado);
     public void eliminar(Long tipoEmpleado);
            
}
