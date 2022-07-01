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
import pe.com.seatle.model.Practica;
import pe.com.seatle.model.Materia;
import pe.com.seatle.model.Tema;
import pe.com.seatle.servicio.AlumnoService;
import pe.com.seatle.servicio.PracticaService;
import pe.com.seatle.servicio.MateriaService;
import pe.com.seatle.servicio.TemaService;

@Controller
@Slf4j
public class ControladorPractica {

    @Autowired
    private PracticaService practicaService;
    
    @Autowired
    private TemaService temaService;
    
    @Autowired
    private AlumnoService alumnoService;
    
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/practica")
    public String practica(Model model) {
        var practica = practicaService.listarPractica();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("practica", practica);
        model.addAttribute("fechaString", fechaString);
        
        return "practicaSEL";
    }

    @GetMapping("/agregarpractica")
    public String agregarpractica(Model model) {
        Practica practica = new Practica();
        List<Tema> tema = temaService.listarTema();
        List<Alumno> alumno = alumnoService.listarAlumno();
        
        model.addAttribute("practica", practica);
        model.addAttribute("tema", tema);
        model.addAttribute("alumno", alumno);
        model.addAttribute("fechaString", fechaString);
        
        return "practicaUPD";
    }

    @PostMapping("/guardarpractica")
    public String guardarpractica(@Valid @ModelAttribute Practica practica, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        List<Tema> tema = temaService.listarTema();
        List<Alumno> alumno = alumnoService.listarAlumno();
        
        if (result.hasErrors()) {
            model.addAttribute("practica", practica);
            model.addAttribute("tema", tema);
            model.addAttribute("alumno", alumno);
            System.out.println("Existen errores en el formulario");
            return "practicaUPD";
        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        practica.setFechaUpdate(fechString + " " + horaString);

        practica.setHostName(check.host().getHostName());
        practica.setIp(check.host().getHostAddress());

        practicaService.guardar(practica);
        System.out.println("Tipo Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Empleado guardado con exito!");
        
        return "redirect:/practica/";
    }

    @GetMapping("/editarpractica/{idPractica}")
    public String editarpractica(@PathVariable("idPractica") Long idPractica,
            Model model, RedirectAttributes attribute) {
            
        Practica practica = null;
        
        if (idPractica > 0) {
            practica = practicaService.encontrarPractica(idPractica);
            if (practica == null) {
                System.out.println("Error: El ID del practica no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del practica no existe!");
                return "practicaUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del practica");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del practica");
            return "practicaUPD";
        }
            
        List<Tema> tema = temaService.listarTema();
        List<Alumno> alumno = alumnoService.listarAlumno();
        
        model.addAttribute("practica", practica);
        model.addAttribute("tema", tema);
        model.addAttribute("alumno", alumno);
        model.addAttribute("fechaString", fechaString);
          
        return "practicaUPD";
    }

    @GetMapping("/eliminarpractica/{idPractica}")
    public String eliminarpractica(@PathVariable("idPractica") Long idPractica,
            RedirectAttributes attribute) {
        
        Practica practica = null;
        
        if (idPractica > 0) {
            practica = practicaService.encontrarPractica(idPractica);
            if (practica == null) {
                System.out.println("Error: El ID del practica no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del practica no existe!");
                return "practicaUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "practicaUPD";
        }
        
        practicaService.eliminar(idPractica);
        System.out.println("Practica eliminado con exito");
        attribute.addFlashAttribute("warning", "Practica eliminado con Exito!");
        
        return "redirect:/practica/";
    }

}
