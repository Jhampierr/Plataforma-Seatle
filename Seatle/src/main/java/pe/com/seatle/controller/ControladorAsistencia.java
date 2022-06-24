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
import pe.com.seatle.model.Alumno;
import pe.com.seatle.util.CheckIP;
import pe.com.seatle.model.Asistencia;
import pe.com.seatle.model.Grado;
import pe.com.seatle.model.Seccion;
import pe.com.seatle.servicio.AlumnoService;
import pe.com.seatle.servicio.AsistenciaService;

@Controller
@Slf4j
public class ControladorAsistencia {

    @Autowired
    private AsistenciaService asistenciaService;
    
    @Autowired
    private AlumnoService alumnoService;
    
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/asistencia")
    public String asistencia(Model model) {
        var asistencia = asistenciaService.listarAsistencia();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("asistencia", asistencia);
        model.addAttribute("fechaString", fechaString);
        
        return "asistenciaSEL";
    }

    @GetMapping("/agregarasistencia")
    public String agregarasistencia(Model model) {
        Asistencia asistencia = new Asistencia();
        List<Alumno> alumno = alumnoService.listarAlumno();
        
        model.addAttribute("asistencia", asistencia);
        model.addAttribute("alumno", alumno);
        model.addAttribute("fechaString", fechaString);
        
        return "asistenciaUPD";
    }

    @PostMapping("/guardarasistencia")
    public String guardarasistencia(@Valid @ModelAttribute Asistencia asistencia, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        List<Alumno> alumno = alumnoService.listarAlumno();
        
        if (result.hasErrors()) {
            model.addAttribute("asistencia", asistencia);
            model.addAttribute("alumno", alumno);
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
            
        List<Alumno> alumno = alumnoService.listarAlumno();
        
        model.addAttribute("asistencia", asistencia);
        model.addAttribute("alumno", alumno);
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
