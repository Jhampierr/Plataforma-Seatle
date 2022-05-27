
package pe.com.sgv.servicio;

import java.util.List;
import pe.com.sgv.model.Empleado;

public interface  EmpleadoService {
    public  List<Empleado> listarEmpleado();
    public  void guardar(Empleado empleado);
    public  Empleado encontrarEmpleado (Long empleado);
    public  void eliminar(Long empleado);        
}
