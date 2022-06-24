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
import pe.com.seatle.model.AulaVirtual;
import pe.com.seatle.model.Curso;
import pe.com.seatle.model.Asistencia;
import pe.com.seatle.servicio.AulaVirtualService;
import pe.com.seatle.servicio.CursoService;
import pe.com.seatle.servicio.AsistenciaService;

@Controller
@Slf4j
public class ControladorAulaVirtual {

    @Autowired
    private AulaVirtualService aulaVirtualService;
    
    @Autowired
    private CursoService cursoService;
    
    @Autowired
    private AsistenciaService asistenciaService;
    
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/aulaVirtual")
    public String aulaVirtual(Model model) {
        var aulaVirtual = aulaVirtualService.listarAulaVirtual();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("aulaVirtual", aulaVirtual);
        model.addAttribute("fechaString", fechaString);
        
        return "aulaVirtualSEL";
    }

    @GetMapping("/agregaraulaVirtual")
    public String agregaraulaVirtual(Model model) {
        AulaVirtual aulaVirtual = new AulaVirtual();
        List<Curso> curso = cursoService.listarCurso();
        List<Asistencia> asistencia = asistenciaService.listarAsistencia();
        
        model.addAttribute("aulaVirtual", aulaVirtual);
        model.addAttribute("curso", curso);
        model.addAttribute("asistencia", asistencia);
        model.addAttribute("fechaString", fechaString);
        
        return "aulaVirtualUPD";
    }

    @PostMapping("/guardaraulaVirtual")
    public String guardaraulaVirtual(@Valid @ModelAttribute AulaVirtual aulaVirtual, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        List<Curso> curso = cursoService.listarCurso();
        List<Asistencia> asistencia = asistenciaService.listarAsistencia();
        
        if (result.hasErrors()) {
            model.addAttribute("aulaVirtual", aulaVirtual);
            model.addAttribute("curso", curso);
            model.addAttribute("asistencia", asistencia);
            System.out.println("Existen errores en el formulario");
            return "aulaVirtualUPD";
        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        aulaVirtual.setFechaUpdate(fechString + " " + horaString);

        aulaVirtual.setHostName(check.host().getHostName());
        aulaVirtual.setIp(check.host().getHostAddress());

        aulaVirtualService.guardar(aulaVirtual);
        System.out.println("Tipo Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Empleado guardado con exito!");
        
        return "redirect:/aulaVirtual/";
    }

    @GetMapping("/editaraulaVirtual/{idAulaVirtual}")
    public String editaraulaVirtual(@PathVariable("idAulaVirtual") Long idAulaVirtual,
            Model model, RedirectAttributes attribute) {
            
        AulaVirtual aulaVirtual = null;
        
        if (idAulaVirtual > 0) {
            aulaVirtual = aulaVirtualService.encontrarAulaVirtual(idAulaVirtual);
            if (aulaVirtual == null) {
                System.out.println("Error: El ID del aulaVirtual no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del aulaVirtual no existe!");
                return "aulaVirtualUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del aulaVirtual");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del aulaVirtual");
            return "aulaVirtualUPD";
        }
            
        List<Curso> curso = cursoService.listarCurso();  
        List<Asistencia> asistencia = asistenciaService.listarAsistencia();
        
        model.addAttribute("aulaVirtual", aulaVirtual);
        model.addAttribute("curso", curso);
        model.addAttribute("asistencia", asistencia);
        model.addAttribute("fechaString", fechaString);
          
        return "aulaVirtualUPD";
    }

    @GetMapping("/eliminaraulaVirtual/{idAulaVirtual}")
    public String eliminaraulaVirtual(@PathVariable("idAulaVirtual") Long idAulaVirtual,
            RedirectAttributes attribute) {
        
        AulaVirtual aulaVirtual = null;
        
        if (idAulaVirtual > 0) {
            aulaVirtual = aulaVirtualService.encontrarAulaVirtual(idAulaVirtual);
            if (aulaVirtual == null) {
                System.out.println("Error: El ID del aulaVirtual no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del aulaVirtual no existe!");
                return "aulaVirtualUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "aulaVirtualUPD";
        }
        
        aulaVirtualService.eliminar(idAulaVirtual);
        System.out.println("AulaVirtual eliminado con exito");
        attribute.addFlashAttribute("warning", "AulaVirtual eliminado con Exito!");
        
        return "redirect:/aulaVirtual/";
    }

}
