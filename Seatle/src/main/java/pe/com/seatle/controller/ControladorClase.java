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
import pe.com.seatle.model.Clase;
import pe.com.seatle.model.Profesor;
import pe.com.seatle.model.Material;
import pe.com.seatle.model.Practica;
import pe.com.seatle.model.Tarea;
import pe.com.seatle.servicio.ProfesorService;
import pe.com.seatle.servicio.MaterialService;
import pe.com.seatle.servicio.PracticaService;
import pe.com.seatle.servicio.TareaService;
import pe.com.seatle.servicio.ClaseService;

@Controller
@Slf4j
public class ControladorClase {

    @Autowired
    private ClaseService claseService;
    
    @Autowired
    private ProfesorService profesorService;
        
    @Autowired
    private MaterialService materialService;
    
    @Autowired
    private TareaService tareaService;
    
    @Autowired
    private PracticaService practicaService;
    
    
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/clase")
    public String clase(Model model) {
        var clase = claseService.listarClase();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("clase", clase);
        model.addAttribute("fechaString", fechaString);
        
        return "claseSEL";
    }
    
//    @GetMapping("/misclases")
//    public String misclases(Model model) {
//        var clase = claseService.listarClase();
//        var profesor = profesorService.listarProfesor();
//                
//        log.info("Ejecutando el controlador Spring MVC");
//        model.addAttribute("clase", clase);
//        model.addAttribute("profesor", profesor);        
//        
//        model.addAttribute("fechaString", fechaString);
//
//        return "misClases";
//    }
    
    @GetMapping("/agregarclase")
    public String agregarclase(Model model) {
        Clase clase = new Clase();
        List<Profesor> profesor = profesorService.listarProfesor();
        List<Material> material = materialService.listarMaterial();
        List<Tarea> tarea = tareaService.listarTarea();
        List<Practica> practica = practicaService.listarPractica();
        
        model.addAttribute("clase", clase);
        model.addAttribute("profesor", profesor);
        model.addAttribute("material", material);
        model.addAttribute("tarea", tarea);
        model.addAttribute("practica", practica);
        model.addAttribute("fechaString", fechaString);
        
        return "claseUPD";
    }
    
//    @GetMapping("/claseClase/{idClase}")
//    public String detalle(@PathVariable("idClase") Long idClase,
//            Model model, RedirectAttributes attribute) {
//
//        Clase clase = null;
//        
//        if (idClase > 0) {
//            clase = claseService.encontrarClase(idClase);
//            if (clase == null) {
//                attribute.addFlashAttribute("error", "ATENCION: El ID del clase no existe!");
//                return "redirect:/clase/";
//            }
//        } else {
//            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del clase");
//            return "redirect:/clase/";
//        }
//
//        model.addAttribute("clase", clase);
//        model.addAttribute("fechaString", fechaString);
//        
//        return "claseClase";
//    }
    
    @PostMapping("/guardarclase")
    public String guardarclase(@Valid @ModelAttribute Clase clase, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        List<Profesor> profesor = profesorService.listarProfesor();
        List<Material> material = materialService.listarMaterial();
        List<Tarea> tarea = tareaService.listarTarea();
        List<Practica> practica = practicaService.listarPractica();
        
        if (result.hasErrors()) {
            model.addAttribute("clase", clase);
            model.addAttribute("profesor", profesor);
            model.addAttribute("material", material);
            model.addAttribute("tarea", tarea);
            model.addAttribute("practica", practica);
            System.out.println("Existen errores en el formulario");
            return "claseUPD";
        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        clase.setFechaUpdate(fechString + " " + horaString);

        clase.setHostName(check.host().getHostName());
        clase.setIp(check.host().getHostAddress());

        claseService.guardar(clase);
        System.out.println("Tipo Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Empleado guardado con exito!");
        
        return "redirect:/clase/";
    }

    @GetMapping("/editarclase/{idClase}")
    public String editarclase(@PathVariable("idClase") Long idClase,
            Model model, RedirectAttributes attribute) {
            
        Clase clase = null;
        
        if (idClase > 0) {
            clase = claseService.encontrarClase(idClase);
            if (clase == null) {
                System.out.println("Error: El ID del clase no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del clase no existe!");
                return "claseUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del clase");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del clase");
            return "claseUPD";
        }
            
        List<Profesor> profesor = profesorService.listarProfesor();
        List<Material> material = materialService.listarMaterial();
        List<Tarea> tarea = tareaService.listarTarea();
        List<Practica> practica = practicaService.listarPractica();
        
        model.addAttribute("clase", clase);
        model.addAttribute("profesor", profesor);
            model.addAttribute("material", material);
            model.addAttribute("tarea", tarea);
            model.addAttribute("practica", practica);
        model.addAttribute("fechaString", fechaString);
          
        return "claseUPD";
    }

    @GetMapping("/eliminarclase/{idClase}")
    public String eliminarclase(@PathVariable("idClase") Long idClase,
            RedirectAttributes attribute) {
        
        Clase clase = null;
        
        if (idClase > 0) {
            clase = claseService.encontrarClase(idClase);
            if (clase == null) {
                System.out.println("Error: El ID del clase no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del clase no existe!");
                return "claseUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "claseUPD";
        }
        
        claseService.eliminar(idClase);
        System.out.println("Clase eliminado con exito");
        attribute.addFlashAttribute("warning", "Clase eliminado con Exito!");
        
        return "redirect:/clase/";
    }

}
