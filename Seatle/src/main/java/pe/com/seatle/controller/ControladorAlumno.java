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
import pe.com.seatle.model.Alumno;
import pe.com.seatle.model.Grado;
import pe.com.seatle.model.Seccion;
import pe.com.seatle.servicio.AlumnoService;
import pe.com.seatle.servicio.GradoService;
import pe.com.seatle.servicio.SeccionService;

@Controller
@Slf4j
public class ControladorAlumno {

    @Autowired
    private AlumnoService alumnoService;
    
    @Autowired
    private GradoService gradoService;
    
    @Autowired
    private SeccionService seccionService;
    
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/alumno")
    public String alumno(Model model) {
        var alumno = alumnoService.listarAlumno();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("alumno", alumno);
        model.addAttribute("fechaString", fechaString);
        
        return "alumnoSEL";
    }

    @GetMapping("/agregaralumno")
    public String agregaralumno(Model model) {
        Alumno alumno = new Alumno();
        List<Grado> grado = gradoService.listarGrado();
        List<Seccion> seccion = seccionService.listarSeccion();
        
        model.addAttribute("alumno", alumno);
        model.addAttribute("grado", grado);
        model.addAttribute("seccion", seccion);
        model.addAttribute("fechaString", fechaString);
        
        return "alumnoUPD";
    }

    @PostMapping("/guardaralumno")
    public String guardaralumno(@Valid @ModelAttribute Alumno alumno, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        List<Grado> grado = gradoService.listarGrado();
        List<Seccion> seccion = seccionService.listarSeccion();
        
        if (result.hasErrors()) {
            model.addAttribute("alumno", alumno);
            model.addAttribute("grado", grado);
            model.addAttribute("seccion", seccion);
            System.out.println("Existen errores en el formulario");
            return "alumnoUPD";
        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        alumno.setFechaUpdate(fechString + " " + horaString);

        alumno.setHostName(check.host().getHostName());
        alumno.setIp(check.host().getHostAddress());

        alumnoService.guardar(alumno);
        System.out.println("Tipo Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Empleado guardado con exito!");
        
        return "redirect:/alumno/";
    }

    @GetMapping("/editaralumno/{idAlumno}")
    public String editaralumno(@PathVariable("idAlumno") Long idAlumno,
            Model model, RedirectAttributes attribute) {
            
        Alumno alumno = null;
        
        if (idAlumno > 0) {
            alumno = alumnoService.encontrarAlumno(idAlumno);
            if (alumno == null) {
                System.out.println("Error: El ID del alumno no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del alumno no existe!");
                return "alumnoUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del alumno");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del alumno");
            return "alumnoUPD";
        }
            
        List<Grado> grado = gradoService.listarGrado();  
        List<Seccion> seccion = seccionService.listarSeccion();
        
        model.addAttribute("alumno", alumno);
        model.addAttribute("grado", grado);
        model.addAttribute("seccion", seccion);
        model.addAttribute("fechaString", fechaString);
          
        return "alumnoUPD";
    }

    @GetMapping("/eliminaralumno/{idAlumno}")
    public String eliminaralumno(@PathVariable("idAlumno") Long idAlumno,
            RedirectAttributes attribute) {
        
        Alumno alumno = null;
        
        if (idAlumno > 0) {
            alumno = alumnoService.encontrarAlumno(idAlumno);
            if (alumno == null) {
                System.out.println("Error: El ID del alumno no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del alumno no existe!");
                return "alumnoUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "alumnoUPD";
        }
        
        alumnoService.eliminar(idAlumno);
        System.out.println("Alumno eliminado con exito");
        attribute.addFlashAttribute("warning", "Alumno eliminado con Exito!");
        
        return "redirect:/alumno/";
    }

}
