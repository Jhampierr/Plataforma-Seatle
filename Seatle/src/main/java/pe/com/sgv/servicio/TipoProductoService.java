package pe.com.sgv.servicio;

import java.util.List;
import pe.com.sgv.model.TipoProducto;

public interface TipoProductoService {
    public List<TipoProducto> listarTipoProducto();
    public void guardar(TipoProducto tipoProducto);
    public TipoProducto encontrarTipoProducto (Long tipoProducto);
    public void eliminar(Long tipoProducto);
    
}
