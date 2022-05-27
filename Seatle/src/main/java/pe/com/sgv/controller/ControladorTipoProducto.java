
package pe.com.sgv.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.com.jp.util.CheckIP;
import pe.com.sgv.model.TipoProducto;
import pe.com.sgv.servicio.TipoProductoService;

@Controller
@Slf4j
public class ControladorTipoProducto {
   
    @Autowired
    private TipoProductoService tipoProductoService;
    
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/tipoProducto")
    public String tipoProducto(Model model){
        var tipoProducto = tipoProductoService.listarTipoProducto();
        
        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("tipoProducto", tipoProducto);
        model.addAttribute("fechaString", fechaString);
        
        return "tipoProductoSEL";
    }
    
    @GetMapping("/agregartipoProducto")
    public String agregartipoProducto(Model model){
        TipoProducto tipoProducto = new TipoProducto();
        model.addAttribute("tipoProducto", tipoProducto);
        model.addAttribute("fechaString", fechaString);
        
        return "tipoProductoUPD";
    }
    
    @PostMapping("/guardartipoProducto")
    public String guardartipoProducto(@Valid @ModelAttribute TipoProducto tipoProducto, BindingResult result, 
            Model model, CheckIP check, RedirectAttributes attribute) {
        if(result.hasErrors()){
            model.addAttribute("tipoProducto", tipoProducto);
            System.out.println("Existen errores en el formulario");
            return "tipoProductoUPD";
        }
        
        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        tipoProducto.setFechaUpdate(fechString + " " + horaString);
        
        tipoProducto.setHostName(check.host().getHostName());
        tipoProducto.setIp(check.host().getHostAddress());
        
        tipoProductoService.guardar(tipoProducto);
        System.out.println("Tipo Producto guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Producto guardado con exito!");
        
        return "redirect:/tipoProducto/";
    }
    
    @GetMapping("/editartipoProducto/{idTipoProducto}")
    public String editartipoProducto(@PathVariable("idTipoProducto") Long idTipoProducto, 
            Model model, RedirectAttributes attribute){
        
        TipoProducto tipoProducto = null;
        
        if (idTipoProducto > 0) {
            tipoProducto = tipoProductoService.encontrarTipoProducto(idTipoProducto);
            if (tipoProducto == null) {
                System.out.println("Error: El ID del tipoProducto no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del tipoProducto no existe!");
                return "tipoEmpleadoUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del tipoProducto");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del tipoProducto");
            return "tipoEmpleadoUPD";
        }
        
       model.addAttribute("tipoProducto", tipoProducto);
       model.addAttribute("fechaString", fechaString);
       
       return "tipoProductoUPD";
    }
    
    @GetMapping("/eliminartipoProducto/{idTipoProducto}")
    public String eliminartipoProducto(@PathVariable("idTipoProducto") Long idTipoProducto, 
            RedirectAttributes attribute){
         
         TipoProducto tipoProducto = null;
        
        if (idTipoProducto > 0) {
            tipoProducto = tipoProductoService.encontrarTipoProducto(idTipoProducto);
            if (tipoProducto == null) {
                System.out.println("Error: El ID del tipoProducto no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del tipoProducto no existe!");
                return "tipoEmpleadoUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del tipoProducto");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del tipoProducto");
            return "tipoEmpleadoUPD";
        }
        
        tipoProductoService.eliminar(idTipoProducto);
        System.out.println("TipoEmpleado eliminado con exito");
        attribute.addFlashAttribute("warning", "TipoEmpleado eliminado con Exito!");
        
        return "redirect:/tipoProducto/";
    }
    
}
