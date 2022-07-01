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
import pe.com.seatle.model.Materia;
import pe.com.seatle.model.Tema;
import pe.com.seatle.servicio.MateriaService;
import pe.com.seatle.servicio.TemaService;

@Controller
@Slf4j
public class ControladorMateria {

    @Autowired
    private MateriaService materiaService;
        
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/materia")
    public String materia(Model model) {
        var materia = materiaService.listarMateria();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("materia", materia);
        model.addAttribute("fechaString", fechaString);
        
        return "materiaSEL";
    }

    @GetMapping("/agregarmateria")
    public String agregarmateria(Model model) {
        Materia materia = new Materia();
        
        model.addAttribute("materia", materia);
        model.addAttribute("fechaString", fechaString);
        
        return "materiaUPD";
    }

    @PostMapping("/guardarmateria")
    public String guardarmateria(@Valid @ModelAttribute Materia materia, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
                
        if (result.hasErrors()) {
            model.addAttribute("materia", materia);
            System.out.println("Existen errores en el formulario");
            return "materiaUPD";
        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        materia.setFechaUpdate(fechString + " " + horaString);

        materia.setHostName(check.host().getHostName());
        materia.setIp(check.host().getHostAddress());

        materiaService.guardar(materia);
        System.out.println("Tipo Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Empleado guardado con exito!");
        
        return "redirect:/materia/";
    }

    @GetMapping("/editarmateria/{idMateria}")
    public String editarmateria(@PathVariable("idMateria") Long idMateria,
            Model model, RedirectAttributes attribute) {
            
        Materia materia = null;
        
        if (idMateria > 0) {
            materia = materiaService.encontrarMateria(idMateria);
            if (materia == null) {
                System.out.println("Error: El ID del materia no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del materia no existe!");
                return "materiaUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del materia");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del materia");
            return "materiaUPD";
        }
            
        model.addAttribute("materia", materia);
        model.addAttribute("fechaString", fechaString);
          
        return "materiaUPD";
    }

    @GetMapping("/eliminarmateria/{idMateria}")
    public String eliminarmateria(@PathVariable("idMateria") Long idMateria,
            RedirectAttributes attribute) {
        
        Materia materia = null;
        
        if (idMateria > 0) {
            materia = materiaService.encontrarMateria(idMateria);
            if (materia == null) {
                System.out.println("Error: El ID del materia no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del materia no existe!");
                return "materiaUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "materiaUPD";
        }
        
        materiaService.eliminar(idMateria);
        System.out.println("Materia eliminado con exito");
        attribute.addFlashAttribute("warning", "Materia eliminado con Exito!");
        
        return "redirect:/materia/";
    }

}
