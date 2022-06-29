package pe.com.seatle.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
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
import pe.com.seatle.util.CheckIP;
import pe.com.seatle.model.Calificaciones;
import pe.com.seatle.model.Clase;
import pe.com.seatle.servicio.CalificacionesService;
import pe.com.seatle.servicio.ClaseService;

@Controller
@Slf4j
public class ControladorCalificaciones {

    @Autowired
    private CalificacionesService calificacionesService;
    
    @Autowired
    private ClaseService claseService;
    
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/calificaciones")
    public String calificaciones(Model model) {
        var calificaciones = calificacionesService.listarCalificaciones();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("calificaciones", calificaciones);
        model.addAttribute("fechaString", fechaString);
        
        return "calificacionesSEL";
    }

    @GetMapping("/agregarcalificaciones")
    public String agregarcalificaciones(Model model) {
        Calificaciones calificaciones = new Calificaciones();
        List<Clase> clase = claseService.listarClase();
        
        model.addAttribute("calificaciones", calificaciones);
        model.addAttribute("clase", clase);
        model.addAttribute("fechaString", fechaString);
        
        return "calificacionesUPD";
    }

    @PostMapping("/guardarcalificaciones")
    public String guardarcalificaciones(@Valid @ModelAttribute Calificaciones calificaciones, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        List<Clase> clase = claseService.listarClase();
        
        if (result.hasErrors()) {
            model.addAttribute("calificaciones", calificaciones);
            model.addAttribute("clase", clase);
            System.out.println("Existen errores en el formulario");
            return "calificacionesUPD";
        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        calificaciones.setFechaUpdate(fechString + " " + horaString);

        calificaciones.setHostName(check.host().getHostName());
        calificaciones.setIp(check.host().getHostAddress());

        calificacionesService.guardar(calificaciones);
        System.out.println("Tipo Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Empleado guardado con exito!");
        
        return "redirect:/calificaciones/";
    }

    @GetMapping("/editarcalificaciones/{idCalificaciones}")
    public String editarcalificaciones(@PathVariable("idCalificaciones") Long idCalificaciones,
            Model model, RedirectAttributes attribute) {
            
        Calificaciones calificaciones = null;
        
        if (idCalificaciones > 0) {
            calificaciones = calificacionesService.encontrarCalificaciones(idCalificaciones);
            if (calificaciones == null) {
                System.out.println("Error: El ID del calificaciones no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del calificaciones no existe!");
                return "calificacionesUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del calificaciones");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del calificaciones");
            return "calificacionesUPD";
        }
            
        List<Clase> clase = claseService.listarClase();  
        
        model.addAttribute("calificaciones", calificaciones);
        model.addAttribute("clase", clase);
        model.addAttribute("fechaString", fechaString);
          
        return "calificacionesUPD";
    }

    @GetMapping("/eliminarcalificaciones/{idCalificaciones}")
    public String eliminarcalificaciones(@PathVariable("idCalificaciones") Long idCalificaciones,
            RedirectAttributes attribute) {
        
        Calificaciones calificaciones = null;
        
        if (idCalificaciones > 0) {
            calificaciones = calificacionesService.encontrarCalificaciones(idCalificaciones);
            if (calificaciones == null) {
                System.out.println("Error: El ID del calificaciones no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del calificaciones no existe!");
                return "calificacionesUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "calificacionesUPD";
        }
        
        calificacionesService.eliminar(idCalificaciones);
        System.out.println("Calificaciones eliminado con exito");
        attribute.addFlashAttribute("warning", "Calificaciones eliminado con Exito!");
        
        return "redirect:/calificaciones/";
    }

}
