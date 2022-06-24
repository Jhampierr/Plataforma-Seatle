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
import pe.com.seatle.model.Profesor;
import pe.com.seatle.model.Grado;
import pe.com.seatle.model.Seccion;
import pe.com.seatle.servicio.ProfesorService;
import pe.com.seatle.servicio.GradoService;
import pe.com.seatle.servicio.SeccionService;

@Controller
@Slf4j
public class ControladorProfesor {

    @Autowired
    private ProfesorService profesorService;
    
    @Autowired
    private GradoService gradoService;
    
    @Autowired
    private SeccionService seccionService;
    
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/profesor")
    public String profesor(Model model) {
        var profesor = profesorService.listarProfesor();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("profesor", profesor);
        model.addAttribute("fechaString", fechaString);
        
        return "profesorSEL";
    }

    @GetMapping("/agregarprofesor")
    public String agregarprofesor(Model model) {
        Profesor profesor = new Profesor();
        List<Grado> grado = gradoService.listarGrado();
        List<Seccion> seccion = seccionService.listarSeccion();
        
        model.addAttribute("profesor", profesor);
        model.addAttribute("grado", grado);
        model.addAttribute("seccion", seccion);
        model.addAttribute("fechaString", fechaString);
        
        return "profesorUPD";
    }

    @PostMapping("/guardarprofesor")
    public String guardarprofesor(@Valid @ModelAttribute Profesor profesor, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        List<Grado> grado = gradoService.listarGrado();
        List<Seccion> seccion = seccionService.listarSeccion();
        
        if (result.hasErrors()) {
            model.addAttribute("profesor", profesor);
            model.addAttribute("grado", grado);
            model.addAttribute("seccion", seccion);
            System.out.println("Existen errores en el formulario");
            return "profesorUPD";
        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        profesor.setFechaUpdate(fechString + " " + horaString);

        profesor.setHostName(check.host().getHostName());
        profesor.setIp(check.host().getHostAddress());

        profesorService.guardar(profesor);
        System.out.println("Tipo Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Empleado guardado con exito!");
        
        return "redirect:/profesor/";
    }

    @GetMapping("/editarprofesor/{idProfesor}")
    public String editarprofesor(@PathVariable("idProfesor") Long idProfesor,
            Model model, RedirectAttributes attribute) {
            
        Profesor profesor = null;
        
        if (idProfesor > 0) {
            profesor = profesorService.encontrarProfesor(idProfesor);
            if (profesor == null) {
                System.out.println("Error: El ID del profesor no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del profesor no existe!");
                return "profesorUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del profesor");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del profesor");
            return "profesorUPD";
        }
            
        List<Grado> grado = gradoService.listarGrado();  
        List<Seccion> seccion = seccionService.listarSeccion();
        
        model.addAttribute("profesor", profesor);
        model.addAttribute("grado", grado);
        model.addAttribute("seccion", seccion);
        model.addAttribute("fechaString", fechaString);
          
        return "profesorUPD";
    }

    @GetMapping("/eliminarprofesor/{idProfesor}")
    public String eliminarprofesor(@PathVariable("idProfesor") Long idProfesor,
            RedirectAttributes attribute) {
        
        Profesor profesor = null;
        
        if (idProfesor > 0) {
            profesor = profesorService.encontrarProfesor(idProfesor);
            if (profesor == null) {
                System.out.println("Error: El ID del profesor no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del profesor no existe!");
                return "profesorUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "profesorUPD";
        }
        
        profesorService.eliminar(idProfesor);
        System.out.println("Profesor eliminado con exito");
        attribute.addFlashAttribute("warning", "Profesor eliminado con Exito!");
        
        return "redirect:/profesor/";
    }

}
