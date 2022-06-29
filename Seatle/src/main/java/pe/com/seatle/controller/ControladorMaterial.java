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
import pe.com.seatle.model.Material;
import pe.com.seatle.model.Materia;
import pe.com.seatle.servicio.MaterialService;
import pe.com.seatle.servicio.MateriaService;

@Controller
@Slf4j
public class ControladorMaterial {

    @Autowired
    private MaterialService materialService;
    
    @Autowired
    private MateriaService materiaService;
    
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/material")
    public String material(Model model) {
        var material = materialService.listarMaterial();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("material", material);
        model.addAttribute("fechaString", fechaString);
        
        return "materialSEL";
    }

    @GetMapping("/agregarmaterial")
    public String agregarmaterial(Model model) {
        Material material = new Material();
        List<Materia> materia = materiaService.listarMateria();
        
        model.addAttribute("material", material);
        model.addAttribute("materia", materia);
        model.addAttribute("fechaString", fechaString);
        
        return "materialUPD";
    }

    @PostMapping("/guardarmaterial")
    public String guardarmaterial(@Valid @ModelAttribute Material material, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        List<Materia> materia = materiaService.listarMateria();
        
        if (result.hasErrors()) {
            model.addAttribute("material", material);
            model.addAttribute("materia", materia);
            System.out.println("Existen errores en el formulario");
            return "materialUPD";
        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        material.setFechaUpdate(fechString + " " + horaString);

        material.setHostName(check.host().getHostName());
        material.setIp(check.host().getHostAddress());

        materialService.guardar(material);
        System.out.println("Tipo Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Empleado guardado con exito!");
        
        return "redirect:/material/";
    }

    @GetMapping("/editarmaterial/{idMaterial}")
    public String editarmaterial(@PathVariable("idMaterial") Long idMaterial,
            Model model, RedirectAttributes attribute) {
            
        Material material = null;
        
        if (idMaterial > 0) {
            material = materialService.encontrarMaterial(idMaterial);
            if (material == null) {
                System.out.println("Error: El ID del material no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del material no existe!");
                return "materialUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del material");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del material");
            return "materialUPD";
        }
            
        List<Materia> materia = materiaService.listarMateria();
        
        model.addAttribute("material", material);
        model.addAttribute("materia", materia);
        model.addAttribute("fechaString", fechaString);
          
        return "materialUPD";
    }

    @GetMapping("/eliminarmaterial/{idMaterial}")
    public String eliminarmaterial(@PathVariable("idMaterial") Long idMaterial,
            RedirectAttributes attribute) {
        
        Material material = null;
        
        if (idMaterial > 0) {
            material = materialService.encontrarMaterial(idMaterial);
            if (material == null) {
                System.out.println("Error: El ID del material no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del material no existe!");
                return "materialUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "materialUPD";
        }
        
        materialService.eliminar(idMaterial);
        System.out.println("Material eliminado con exito");
        attribute.addFlashAttribute("warning", "Material eliminado con Exito!");
        
        return "redirect:/material/";
    }

}
