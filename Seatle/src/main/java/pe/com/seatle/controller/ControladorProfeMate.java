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
import pe.com.seatle.model.ProfeMate;
import pe.com.seatle.model.Profesor;
import pe.com.seatle.servicio.MateriaService;
import pe.com.seatle.servicio.ProfeMateService;
import pe.com.seatle.servicio.ProfesorService;

@Controller
@Slf4j
public class ControladorProfeMate {

    @Autowired
    private ProfeMateService profeMateService;
    
    @Autowired
    private ProfesorService profesorService;
    
    @Autowired
    private MateriaService materiaService;
        
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/profeMate")
    public String profeMate(Model model) {
        var profeMate = profeMateService.listarProfeMate();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("profeMate", profeMate);
        model.addAttribute("fechaString", fechaString);
        
        return "profeMateSEL";
    }

    @GetMapping("/agregarprofeMate")
    public String agregarprofeMate(Model model) {
        ProfeMate profeMate = new ProfeMate();
        List<Profesor> profesor = profesorService.listarProfesor();
        List<Materia> materia = materiaService.listarMateria();
        
        model.addAttribute("profeMate", profeMate);
        model.addAttribute("profesor", profesor);
        model.addAttribute("materia", materia);
        model.addAttribute("fechaString", fechaString);
        
        return "profeMateUPD";
    }

    @PostMapping("/guardarprofeMate")
    public String guardarprofeMate(@Valid @ModelAttribute ProfeMate profeMate, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        List<Profesor> profesor = profesorService.listarProfesor();
        List<Materia> materia = materiaService.listarMateria();
        
        if (result.hasErrors()) {
            model.addAttribute("profeMate", profeMate);
            model.addAttribute("profesor", profesor);
            model.addAttribute("materia", materia);
            System.out.println("Existen errores en el formulario");
            return "profeMateUPD";
        }

        profeMateService.guardar(profeMate);
        System.out.println("Tipo Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Empleado guardado con exito!");
        
        return "redirect:/profeMate/";
    }

    @GetMapping("/editarprofeMate/{idProfeMate}")
    public String editarprofeMate(@PathVariable("idProfeMate") Long idProfeMate,
            Model model, RedirectAttributes attribute) {
            
        ProfeMate profeMate = null;
        
        if (idProfeMate > 0) {
            profeMate = profeMateService.encontrarProfeMate(idProfeMate);
            if (profeMate == null) {
                System.out.println("Error: El ID del profeMate no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del profeMate no existe!");
                return "profeMateUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del profeMate");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del profeMate");
            return "profeMateUPD";
        }
        
        List<Profesor> profesor = profesorService.listarProfesor();
        List<Materia> materia = materiaService.listarMateria();
        
        model.addAttribute("profeMate", profeMate);
        model.addAttribute("profesor", profesor);
        model.addAttribute("materia", materia);
        model.addAttribute("fechaString", fechaString);
          
        return "profeMateUPD";
    }

    @GetMapping("/eliminarprofeMate/{idProfeMate}")
    public String eliminarprofeMate(@PathVariable("idProfeMate") Long idProfeMate,
            RedirectAttributes attribute) {
        
        ProfeMate profeMate = null;
        
        if (idProfeMate > 0) {
            profeMate = profeMateService.encontrarProfeMate(idProfeMate);
            if (profeMate == null) {
                System.out.println("Error: El ID del profeMate no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del profeMate no existe!");
                return "profeMateUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "profeMateUPD";
        }
        
        profeMateService.eliminar(idProfeMate);
        System.out.println("ProfeMate eliminado con exito");
        attribute.addFlashAttribute("warning", "ProfeMate eliminado con Exito!");
        
        return "redirect:/profeMate/";
    }

}
