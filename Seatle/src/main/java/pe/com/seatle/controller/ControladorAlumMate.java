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
import pe.com.seatle.model.Materia;
import pe.com.seatle.util.CheckIP;
import pe.com.seatle.model.AlumMate;
import pe.com.seatle.model.Alumno;
import pe.com.seatle.servicio.MateriaService;
import pe.com.seatle.servicio.AlumMateService;
import pe.com.seatle.servicio.AlumnoService;

@Controller
@Slf4j
public class ControladorAlumMate {

    @Autowired
    private AlumMateService alumMateService;
    
    @Autowired
    private AlumnoService alumnoService;
    
    @Autowired
    private MateriaService materiaService;
        
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/alumMate")
    public String alumMate(Model model) {
        var alumMate = alumMateService.listarAlumMate();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("alumMate", alumMate);
        model.addAttribute("fechaString", fechaString);
        
        return "alumMateSEL";
    }

    @GetMapping("/agregaralumMate")
    public String agregaralumMate(Model model) {
        AlumMate alumMate = new AlumMate();
        List<Alumno> alumno = alumnoService.listarAlumno();
        List<Materia> materia = materiaService.listarMateria();
        
        model.addAttribute("alumMate", alumMate);
        model.addAttribute("alumno", alumno);
        model.addAttribute("materia", materia);
        model.addAttribute("fechaString", fechaString);
        
        return "alumMateUPD";
    }

    @PostMapping("/guardaralumMate")
    public String guardaralumMate(@Valid @ModelAttribute AlumMate alumMate, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        List<Alumno> alumno = alumnoService.listarAlumno();
        List<Materia> materia = materiaService.listarMateria();
        
        if (result.hasErrors()) {
            model.addAttribute("alumMate", alumMate);
            model.addAttribute("alumno", alumno);
            model.addAttribute("materia", materia);
            System.out.println("Existen errores en el formulario");
            return "alumMateUPD";
        }

        alumMateService.guardar(alumMate);
        System.out.println("Tipo Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Empleado guardado con exito!");
        
        return "redirect:/alumMate/";
    }

    @GetMapping("/editaralumMate/{idAlumMate}")
    public String editaralumMate(@PathVariable("idAlumMate") Long idAlumMate,
            Model model, RedirectAttributes attribute) {
            
        AlumMate alumMate = null;
        
        if (idAlumMate > 0) {
            alumMate = alumMateService.encontrarAlumMate(idAlumMate);
            if (alumMate == null) {
                System.out.println("Error: El ID del alumMate no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del alumMate no existe!");
                return "alumMateUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del alumMate");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del alumMate");
            return "alumMateUPD";
        }
        
        List<Alumno> alumno = alumnoService.listarAlumno();
        List<Materia> materia = materiaService.listarMateria();
        
        model.addAttribute("alumMate", alumMate);
        model.addAttribute("alumno", alumno);
        model.addAttribute("materia", materia);
        model.addAttribute("fechaString", fechaString);
          
        return "alumMateUPD";
    }

    @GetMapping("/eliminaralumMate/{idAlumMate}")
    public String eliminaralumMate(@PathVariable("idAlumMate") Long idAlumMate,
            RedirectAttributes attribute) {
        
        AlumMate alumMate = null;
        
        if (idAlumMate > 0) {
            alumMate = alumMateService.encontrarAlumMate(idAlumMate);
            if (alumMate == null) {
                System.out.println("Error: El ID del alumMate no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del alumMate no existe!");
                return "alumMateUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "alumMateUPD";
        }
        
        alumMateService.eliminar(idAlumMate);
        System.out.println("AlumMate eliminado con exito");
        attribute.addFlashAttribute("warning", "AlumMate eliminado con Exito!");
        
        return "redirect:/alumMate/";
    }

}
