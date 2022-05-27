package pe.com.sgv.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
import pe.com.sgv.model.Cliente;
import pe.com.sgv.servicio.ClienteService;

@Controller
@Slf4j
public class ControladorCliente {

    @Autowired
    private ClienteService clienteService;
    
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/cliente")
    public String cliente(Model model) {
        var cliente = clienteService.listarCliente();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("cliente", cliente);
        model.addAttribute("fechaString", fechaString);
        
        return "clienteSEL";
    }

    @GetMapping("/agregarcliente")
    public String agregarcliente(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        model.addAttribute("fechaString", fechaString);
        
        return "clienteUPD";
    }

    @PostMapping("/guardarcliente")
    public String guardarcliente(@Valid @ModelAttribute Cliente cliente, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        if (result.hasErrors()) {
            model.addAttribute("cliente", cliente);
            System.out.println("Existen errores en el formulario");
            return "clienteUPD";
        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        cliente.setFechaUpdate(fechString + " " + horaString);

        cliente.setHostName(check.host().getHostName());
        cliente.setIp(check.host().getHostAddress());

        clienteService.guardar(cliente);
        System.out.println("Tipo Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Empleado guardado con exito!");
        
        return "redirect:/cliente/";
    }

    @GetMapping("/editarcliente/{idCliente}")
    public String editarcliente(@PathVariable("idCliente") Long idCliente,
            Model model, RedirectAttributes attribute) {
            
        Cliente cliente = null;
        
        if (idCliente > 0) {
            cliente = clienteService.encontrarCliente(idCliente);
            if (cliente == null) {
                System.out.println("Error: El ID del cliente no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del cliente no existe!");
                return "clienteUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del cliente");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del cliente");
            return "clienteUPD";
        }
        
          model.addAttribute("cliente", cliente);
          model.addAttribute("fechaString", fechaString);
          
        return "clienteUPD";
    }

    @GetMapping("/eliminarcliente/{idCliente}")
    public String eliminarcliente(@PathVariable("idCliente") Long idCliente,
            RedirectAttributes attribute) {
        
        Cliente cliente = null;
        
        if (idCliente > 0) {
            cliente = clienteService.encontrarCliente(idCliente);
            if (cliente == null) {
                System.out.println("Error: El ID del cliente no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del cliente no existe!");
                return "clienteUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Empleado");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del empleado");
            return "clienteUPD";
        }
        
        clienteService.eliminar(idCliente);
        System.out.println("Cliente eliminado con exito");
        attribute.addFlashAttribute("warning", "Cliente eliminado con Exito!");
        
        return "redirect:/cliente/";
    }

}
