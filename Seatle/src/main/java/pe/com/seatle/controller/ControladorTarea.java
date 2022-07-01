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
import pe.com.seatle.model.Tarea;
import pe.com.seatle.model.Tema;
import pe.com.seatle.servicio.AlumnoService;
import pe.com.seatle.servicio.TareaService;
import pe.com.seatle.servicio.TemaService;

@Controller
@Slf4j
public class ControladorTarea {

    @Autowired
    private TareaService tareaService;
    
    @Autowired
    private TemaService temaService;
    
    @Autowired
    private AlumnoService alumnoService;
    
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/tarea")
    public String tarea(Model model) {
        var tarea = tareaService.listarTarea();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("tarea", tarea);
        model.addAttribute("fechaString", fechaString);
        
        return "tareaSEL";
    }

    @GetMapping("/agregartarea")
    public String agregartarea(Model model) {
        Tarea tarea = new Tarea();
        List<Tema> tema = temaService.listarTema();
        List<Alumno> alumno = alumnoService.listarAlumno();
        
        model.addAttribute("tarea", tarea);
        model.addAttribute("tema", tema);
        model.addAttribute("alumno", alumno);
        model.addAttribute("fechaString", fechaString);
        
        return "tareaUPD";
    }

    @PostMapping("/guardartarea")
    public String guardartarea(@Valid @ModelAttribute Tarea tarea, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        List<Tema> tema = temaService.listarTema();
        List<Alumno> alumno = alumnoService.listarAlumno();
        
        if (result.hasErrors()) {
            model.addAttribute("tarea", tarea);
            model.addAttribute("tema", tema);
            model.addAttribute("alumno", alumno);
            System.out.println("Existen errores en el formulario");
            return "tareaUPD";
        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        tarea.setFechaUpdate(fechString + " " + horaString);

        tarea.setHostName(check.host().getHostName());
        tarea.setIp(check.host().getHostAddress());

        tareaService.guardar(tarea);
        System.out.println("Tipo Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Empleado guardado con exito!");
        
        return "redirect:/tarea/";
    }

    @GetMapping("/editartarea/{idTarea}")
    public String editartarea(@PathVariable("idTarea") Long idTarea,
            Model model, RedirectAttributes attribute) {
            
        Tarea tarea = null;
        
        if (idTarea > 0) {
            tarea = tareaService.encontrarTarea(idTarea);
            if (tarea == null) {
                System.out.println("Error: El ID del tarea no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del tarea no existe!");
                return "tareaUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del tarea");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del tarea");
            return "tareaUPD";
        }
            
        List<Tema> tema = temaService.listarTema();
        List<Alumno> alumno = alumnoService.listarAlumno();
        
        model.addAttribute("tarea", tarea);
        model.addAttribute("tema", tema);
        model.addAttribute("alumno", alumno);
        model.addAttribute("fechaString", fechaString);
          
        return "tareaUPD";
    }

    @GetMapping("/eliminartarea/{idTarea}")
    public String eliminartarea(@PathVariable("idTarea") Long idTarea,
            RedirectAttributes attribute) {
        
        Tarea tarea = null;
        
        if (idTarea > 0) {
            tarea = tareaService.encontrarTarea(idTarea);
            if (tarea == null) {
                System.out.println("Error: El ID del tarea no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del tarea no existe!");
                return "tareaUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "tareaUPD";
        }
        
        tareaService.eliminar(idTarea);
        System.out.println("Tarea eliminado con exito");
        attribute.addFlashAttribute("warning", "Tarea eliminado con Exito!");
        
        return "redirect:/tarea/";
    }

}
