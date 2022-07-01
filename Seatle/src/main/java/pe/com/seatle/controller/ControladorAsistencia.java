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
import pe.com.seatle.model.AlumMate;
import pe.com.seatle.util.CheckIP;
import pe.com.seatle.model.Asistencia;
import pe.com.seatle.servicio.AlumMateService;
import pe.com.seatle.servicio.AsistenciaService;

@Controller
@Slf4j
public class ControladorAsistencia {

    @Autowired
    private AsistenciaService asistenciaService;
    
    @Autowired
    private AlumMateService alumMateService;
    
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/asistencia")
    public String asistencia(Model model) {
        var asistencia = asistenciaService.listarAsistencia();
        var alumMate = alumMateService.listarAlumMate();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("asistencia", asistencia);
        model.addAttribute("alumMate", alumMate);
        model.addAttribute("fechaString", fechaString);
        
        return "asistenciaSEL";
    }

    @GetMapping("/miAsistencia/{idAsistencia}")
    public String miAsistencia(@PathVariable("idAsistencia") Long idAsistencia,
            Model model, RedirectAttributes attribute) {

        
        Asistencia asistencia = null;
        AlumMate alumMate = null;
        
        if (idAsistencia > 0) {
            asistencia = asistenciaService.encontrarAsistencia(idAsistencia);
            
            if (asistencia == null) {
                attribute.addFlashAttribute("error", "ATENCION: El ID del aulaVirtual no existe!");
                return "redirect:/asistencia/";
            }
        } else {
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del aulaVirtual");
            return "redirect:/asistencia/";
        }

        model.addAttribute("asistencia", asistencia);
        model.addAttribute("alumMate", alumMate);
        model.addAttribute("fechaString", fechaString);
        
        return "asistenciaDetalle";
    }
    
    @GetMapping("/agregarasistencia")
    public String agregarasistencia(Model model) {
        Asistencia asistencia = new Asistencia();
        List<AlumMate> alumMate = alumMateService.listarAlumMate();
        
        model.addAttribute("asistencia", asistencia);
        model.addAttribute("alumMate", alumMate);
        model.addAttribute("fechaString", fechaString);
        
        return "asistenciaUPD";
    }

    @PostMapping("/guardarasistencia")
    public String guardarasistencia(@Valid @ModelAttribute Asistencia asistencia, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        List<AlumMate> alumMate = alumMateService.listarAlumMate();
        
        if (result.hasErrors()) {
            model.addAttribute("asistencia", asistencia);
            model.addAttribute("alumMate", alumMate);
            System.out.println("Existen errores en el formulario");
            return "asistenciaUPD";
        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        asistencia.setFechaUpdate(fechString + " " + horaString);

        asistencia.setHostName(check.host().getHostName());
        asistencia.setIp(check.host().getHostAddress());

        asistenciaService.guardar(asistencia);
        System.out.println("Tipo Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Empleado guardado con exito!");
        
        return "redirect:/asistencia/";
    }

    @GetMapping("/editarasistencia/{idAsistencia}")
    public String editarasistencia(@PathVariable("idAsistencia") Long idAsistencia,
            Model model, RedirectAttributes attribute) {
            
        Asistencia asistencia = null;
        
        if (idAsistencia > 0) {
            asistencia = asistenciaService.encontrarAsistencia(idAsistencia);
            if (asistencia == null) {
                System.out.println("Error: El ID del asistencia no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del asistencia no existe!");
                return "asistenciaUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del asistencia");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del asistencia");
            return "asistenciaUPD";
        }
            
        List<AlumMate> alumMate = alumMateService.listarAlumMate();
        
        model.addAttribute("asistencia", asistencia);
        model.addAttribute("alumMate", alumMate);
        model.addAttribute("fechaString", fechaString);
          
        return "asistenciaUPD";
    }

    @GetMapping("/eliminarasistencia/{idAsistencia}")
    public String eliminarasistencia(@PathVariable("idAsistencia") Long idAsistencia,
            RedirectAttributes attribute) {
        
        Asistencia asistencia = null;
        
        if (idAsistencia > 0) {
            asistencia = asistenciaService.encontrarAsistencia(idAsistencia);
            if (asistencia == null) {
                System.out.println("Error: El ID del asistencia no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del asistencia no existe!");
                return "asistenciaUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "asistenciaUPD";
        }
        
        asistenciaService.eliminar(idAsistencia);
        System.out.println("Asistencia eliminado con exito");
        attribute.addFlashAttribute("warning", "Asistencia eliminado con Exito!");
        
        return "redirect:/asistencia/";
    }

}
