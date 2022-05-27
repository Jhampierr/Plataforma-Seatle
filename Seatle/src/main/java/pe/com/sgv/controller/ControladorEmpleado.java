    package pe.com.sgv.controller;

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
import pe.com.jp.util.CheckIP;
import pe.com.sgv.model.Empleado;
import pe.com.sgv.model.TipoEmpleado;
import pe.com.sgv.servicio.EmpleadoService;
import pe.com.sgv.servicio.TipoEmpleadoService;

@Controller
@Slf4j
public class ControladorEmpleado {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private TipoEmpleadoService tipoEmpService;
    
    String fechaString = LocalDate.now().toString();

    @GetMapping("/empleado")
    public String empleado(Model model) {
        var empleado = empleadoService.listarEmpleado();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("empleado", empleado);
        model.addAttribute("fechaString", fechaString);
        
        return "empleadoSEL";
    }

    @GetMapping("/agregarempleado")
    public String agregar(Model model) {
        Empleado empleado = new Empleado();
        List<TipoEmpleado> tipoEmp = tipoEmpService.listarTipoEmpleado();

        model.addAttribute("empleado", empleado);
        model.addAttribute("tipoEmp", tipoEmp);
        model.addAttribute("fechaString", fechaString);
        
        return "empleadoUPD";
    }

    @PostMapping("/guardarempleado")
    public String guardar(@Valid @ModelAttribute Empleado empleado, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {

        List<TipoEmpleado> tipoEmp = tipoEmpService.listarTipoEmpleado();

        if (result.hasErrors()) {
            model.addAttribute("empleado", empleado);
            model.addAttribute("tipoEmp", tipoEmp);
            System.out.println("Existen errores en el formulario");
            return "empleadoUPD";
        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        empleado.setFechaUpdate(fechString + " " + horaString);

        empleado.setHostName(check.host().getHostName());
        empleado.setIp(check.host().getHostAddress());

        empleadoService.guardar(empleado);
        System.out.println("Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Empleado guardado con exito!");
        
        return "redirect:/empleado/";
    }

    @GetMapping("/editarempleado/{idEmpleado}")
    public String editar(@PathVariable("idEmpleado") Long idEmpleado,
            Model model, RedirectAttributes attribute) {

        Empleado empleado = null;

        if (idEmpleado > 0) {
            empleado = empleadoService.encontrarEmpleado(idEmpleado);
            if (empleado == null) {
                System.out.println("Error: El ID del empleado no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del empleado no existe!");
                return "empleadoUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "empleadoUPD";
        }

        List<TipoEmpleado> tipoEmp = tipoEmpService.listarTipoEmpleado();

        model.addAttribute("empleado", empleado);
        model.addAttribute("tipoEmp", tipoEmp);
        model.addAttribute("fechaString", fechaString);
        
        return "empleadoUPD";
    }

    @GetMapping("/eliminarempleado/{idEmpleado}")
    public String eliminar(@PathVariable("idEmpleado") Long idEmpleado,
            RedirectAttributes attribute) {

        Empleado empleado = null;

        if (idEmpleado > 0) {
            empleado = empleadoService.encontrarEmpleado(idEmpleado);
            if (empleado == null) {
                System.out.println("Error: El ID del empleado no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del empleado no existe!");
                return "empleadoUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "empleadoUPD";
        }

        empleadoService.eliminar(idEmpleado);
        System.out.println("Empleado eliminado con exito");
        attribute.addFlashAttribute("warning", "Empleado eliminado con Exito!");
        return "redirect:/empleado/";
    }

}
