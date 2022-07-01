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
import pe.com.seatle.model.Tema;
import pe.com.seatle.servicio.MateriaService;
import pe.com.seatle.servicio.TemaService;

@Controller
@Slf4j
public class ControladorTema {

    @Autowired
    private TemaService temaService;
    
    @Autowired
    private MateriaService materiaService;
        
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/tema")
    public String tema(Model model) {
        var tema = temaService.listarTema();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("tema", tema);
        model.addAttribute("fechaString", fechaString);
        
        return "temaSEL";
    }

    @GetMapping("/agregartema")
    public String agregartema(Model model) {
        Tema tema = new Tema();
        List<Materia> materia = materiaService.listarMateria();
        
        model.addAttribute("tema", tema);
        model.addAttribute("materia", materia);
        model.addAttribute("fechaString", fechaString);
        
        return "temaUPD";
    }

    @PostMapping("/guardartema")
    public String guardartema(@Valid @ModelAttribute Tema tema, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        List<Materia> materia = materiaService.listarMateria();
        
        if (result.hasErrors()) {
            model.addAttribute("tema", tema);
            model.addAttribute("materia", materia);
            System.out.println("Existen errores en el formulario");
            return "temaUPD";
        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        tema.setFechaUpdate(fechString + " " + horaString);

        tema.setHostName(check.host().getHostName());
        tema.setIp(check.host().getHostAddress());

        temaService.guardar(tema);
        System.out.println("Tipo Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Empleado guardado con exito!");
        
        return "redirect:/tema/";
    }

    @GetMapping("/editartema/{idTema}")
    public String editartema(@PathVariable("idTema") Long idTema,
            Model model, RedirectAttributes attribute) {
            
        Tema tema = null;
        
        if (idTema > 0) {
            tema = temaService.encontrarTema(idTema);
            if (tema == null) {
                System.out.println("Error: El ID del tema no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del tema no existe!");
                return "temaUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del tema");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del tema");
            return "temaUPD";
        }
        
        List<Materia> materia = materiaService.listarMateria();
        
        model.addAttribute("tema", tema);
        model.addAttribute("materia", materia);
        model.addAttribute("fechaString", fechaString);
          
        return "temaUPD";
    }

    @GetMapping("/eliminartema/{idTema}")
    public String eliminartema(@PathVariable("idTema") Long idTema,
            RedirectAttributes attribute) {
        
        Tema tema = null;
        
        if (idTema > 0) {
            tema = temaService.encontrarTema(idTema);
            if (tema == null) {
                System.out.println("Error: El ID del tema no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del tema no existe!");
                return "temaUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "temaUPD";
        }
        
        temaService.eliminar(idTema);
        System.out.println("Tema eliminado con exito");
        attribute.addFlashAttribute("warning", "Tema eliminado con Exito!");
        
        return "redirect:/tema/";
    }

}
