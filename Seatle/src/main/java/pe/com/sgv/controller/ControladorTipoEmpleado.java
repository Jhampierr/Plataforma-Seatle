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
import pe.com.sgv.model.TipoEmpleado;
import pe.com.sgv.servicio.TipoEmpleadoService;

@Controller
@Slf4j
public class ControladorTipoEmpleado {

    @Autowired
    private TipoEmpleadoService tipoEmpleadoService;
    
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/tipoEmpleado")
    public String tipoEmpleado(Model model) {
        var tipoEmpleado = tipoEmpleadoService.listarTipoEmpleado();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("tipoEmpleado", tipoEmpleado);
        model.addAttribute("fechaString", fechaString);
        
        return "tipoEmpleadoSEL";
    }

    @GetMapping("/agregartipoEmpleado")
    public String agregartipoEmpleado(Model model) {
        TipoEmpleado tipoEmpleado = new TipoEmpleado();
        model.addAttribute("tipoEmpleado", tipoEmpleado);
        model.addAttribute("fechaString", fechaString);
        
        return "tipoEmpleadoUPD";
    }

    @PostMapping("/guardartipoEmpleado")
    public String guardartipoEmpleado(@Valid @ModelAttribute TipoEmpleado tipoEmpleado, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        if (result.hasErrors()) {
            model.addAttribute("tipoEmpleado", tipoEmpleado);
            System.out.println("Existen errores en el formulario");
            return "tipoEmpleadoUPD";
        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        tipoEmpleado.setFechaUpdate(fechString + " " + horaString);

        tipoEmpleado.setHostName(check.host().getHostName());
        tipoEmpleado.setIp(check.host().getHostAddress());

        tipoEmpleadoService.guardar(tipoEmpleado);
        System.out.println("Tipo Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Empleado guardado con exito!");
        
        return "redirect:/tipoEmpleado/";
    }

    @GetMapping("/editartipoEmpleado/{idTipoEmpleado}")
    public String editartipoEmpleado(@PathVariable("idTipoEmpleado") Long idTipoEmpleado,
            Model model, RedirectAttributes attribute) {
            
        TipoEmpleado tipoEmpleado = null;
        
        if (idTipoEmpleado > 0) {
            tipoEmpleado = tipoEmpleadoService.encontrarTipoEmpleado(idTipoEmpleado);
            if (tipoEmpleado == null) {
                System.out.println("Error: El ID del tipoEmpleado no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del tipoEmpleado no existe!");
                return "tipoEmpleadoUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del tipoEmpleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del tipoEmpleado");
            return "tipoEmpleadoUPD";
        }
        
          model.addAttribute("tipoEmpleado", tipoEmpleado);
          model.addAttribute("fechaString", fechaString);
          
        return "tipoEmpleadoUPD";
    }

    @GetMapping("/eliminartipoEmpleado/{idTipoEmpleado}")
    public String eliminartipoEmpleado(@PathVariable("idTipoEmpleado") Long idTipoEmpleado,
            RedirectAttributes attribute) {
        
        TipoEmpleado tipoEmpleado = null;
        
        if (idTipoEmpleado > 0) {
            tipoEmpleado = tipoEmpleadoService.encontrarTipoEmpleado(idTipoEmpleado);
            if (tipoEmpleado == null) {
                System.out.println("Error: El ID del tipoEmpleado no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del tipoEmpleado no existe!");
                return "tipoEmpleadoUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "tipoEmpleadoUPD";
        }
        
        tipoEmpleadoService.eliminar(idTipoEmpleado);
        System.out.println("TipoEmpleado eliminado con exito");
        attribute.addFlashAttribute("warning", "TipoEmpleado eliminado con Exito!");
        
        return "redirect:/tipoEmpleado/";
    }

}
