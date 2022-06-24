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
import pe.com.seatle.model.Curso;
import pe.com.seatle.model.Grado;
import pe.com.seatle.model.Material;
import pe.com.seatle.model.Practica;
import pe.com.seatle.model.Tarea;
import pe.com.seatle.model.Tema;
import pe.com.seatle.servicio.GradoService;
import pe.com.seatle.servicio.CursoService;
import pe.com.seatle.servicio.MaterialService;
import pe.com.seatle.servicio.PracticaService;
import pe.com.seatle.servicio.TareaService;
import pe.com.seatle.servicio.TemaService;

@Controller
@Slf4j
public class ControladorCurso {

    @Autowired
    private CursoService cursoService;
    
    @Autowired
    private GradoService gradoService;
    
    @Autowired
    private TemaService temaService;
    
    @Autowired
    private MaterialService materialService;
    
    @Autowired
    private TareaService tareaService;
    
    @Autowired
    private PracticaService practicaService;
    
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/curso")
    public String curso(Model model) {
        var curso = cursoService.listarCurso();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("curso", curso);
        model.addAttribute("fechaString", fechaString);
        
        return "cursoSEL";
    }
    
    @GetMapping("/miscursos")
    public String miscursos(Model model) {
        var curso = cursoService.listarCurso();
        var grado = gradoService.listarGrado();
                
        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("curso", curso);
        model.addAttribute("grado", grado);
        
        model.addAttribute("fechaString", fechaString);

        return "mis_cursos";
    }
    
    @GetMapping("/agregarcurso")
    public String agregarcurso(Model model) {
        Curso curso = new Curso();
        List<Grado> grado = gradoService.listarGrado();
        List<Tema> tema = temaService.listarTema();
        List<Material> material = materialService.listarMaterial();
        List<Tarea> tarea = tareaService.listarTarea();
        List<Practica> practica = practicaService.listarPractica();
        
        model.addAttribute("curso", curso);
        model.addAttribute("grado", grado);
        model.addAttribute("tema", tema);
        model.addAttribute("material", material);
        model.addAttribute("tarea", tarea);
        model.addAttribute("practica", practica);
        model.addAttribute("fechaString", fechaString);
        
        return "cursoUPD";
    }

    @PostMapping("/guardarcurso")
    public String guardarcurso(@Valid @ModelAttribute Curso curso, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        List<Grado> grado = gradoService.listarGrado();
        List<Tema> tema = temaService.listarTema();
        List<Material> material = materialService.listarMaterial();
        List<Tarea> tarea = tareaService.listarTarea();
        List<Practica> practica = practicaService.listarPractica();
        
        if (result.hasErrors()) {
            model.addAttribute("curso", curso);
            model.addAttribute("grado", grado);
            model.addAttribute("tema", tema);
            model.addAttribute("material", material);
            model.addAttribute("tarea", tarea);
            model.addAttribute("practica", practica);
            System.out.println("Existen errores en el formulario");
            return "cursoUPD";
        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        curso.setFechaUpdate(fechString + " " + horaString);

        curso.setHostName(check.host().getHostName());
        curso.setIp(check.host().getHostAddress());

        cursoService.guardar(curso);
        System.out.println("Tipo Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Empleado guardado con exito!");
        
        return "redirect:/curso/";
    }

    @GetMapping("/editarcurso/{idCurso}")
    public String editarcurso(@PathVariable("idCurso") Long idCurso,
            Model model, RedirectAttributes attribute) {
            
        Curso curso = null;
        
        if (idCurso > 0) {
            curso = cursoService.encontrarCurso(idCurso);
            if (curso == null) {
                System.out.println("Error: El ID del curso no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del curso no existe!");
                return "cursoUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del curso");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del curso");
            return "cursoUPD";
        }
            
        List<Grado> grado = gradoService.listarGrado();
        List<Tema> tema = temaService.listarTema();
        List<Material> material = materialService.listarMaterial();
        List<Tarea> tarea = tareaService.listarTarea();
        List<Practica> practica = practicaService.listarPractica();
        
        model.addAttribute("curso", curso);
        model.addAttribute("grado", grado);
        model.addAttribute("tema", tema);
            model.addAttribute("material", material);
            model.addAttribute("tarea", tarea);
            model.addAttribute("practica", practica);
        model.addAttribute("fechaString", fechaString);
          
        return "cursoUPD";
    }

    @GetMapping("/eliminarcurso/{idCurso}")
    public String eliminarcurso(@PathVariable("idCurso") Long idCurso,
            RedirectAttributes attribute) {
        
        Curso curso = null;
        
        if (idCurso > 0) {
            curso = cursoService.encontrarCurso(idCurso);
            if (curso == null) {
                System.out.println("Error: El ID del curso no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del curso no existe!");
                return "cursoUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "cursoUPD";
        }
        
        cursoService.eliminar(idCurso);
        System.out.println("Curso eliminado con exito");
        attribute.addFlashAttribute("warning", "Curso eliminado con Exito!");
        
        return "redirect:/curso/";
    }

}
