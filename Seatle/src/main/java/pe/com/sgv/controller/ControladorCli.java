package pe.com.sgv.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.com.jp.util.CheckIP;
import pe.com.sgv.model.Cliente;
import pe.com.sgv.model.Empleado;
import pe.com.sgv.model.Oferta;
import pe.com.sgv.model.Pedido;
import pe.com.sgv.model.Producto;
import pe.com.sgv.model.Reserva;
import pe.com.sgv.servicio.ClienteService;
import pe.com.sgv.servicio.EmpleadoService;
import pe.com.sgv.servicio.OfertaService;
import pe.com.sgv.servicio.PedidoService;
import pe.com.sgv.servicio.ProductoService;
import pe.com.sgv.servicio.ReservaService;

@Controller
@Slf4j
public class ControladorCli {

    @Autowired
    private ReservaService reservaService;
    
    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private OfertaService ofertaService;
    
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/web")
    public String web() {
                
        return "web";
    }
    
    @GetMapping("/acerca")
    public String acerca() {
                
        return "about";
    }
    
    @GetMapping("/contacto")
    public String contacto() {
                
        return "contact";
    }
    
    @GetMapping("/menureservaC")
    public String menu(Model model) {
        Reserva reserva = new Reserva();
        List<Cliente> cliente2 = clienteService.listarCliente();
        
        Cliente cliente = new Cliente();
                        
        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("reserva", reserva);
        model.addAttribute("cliente2", cliente2);
        
        model.addAttribute("cliente", cliente);
        
        model.addAttribute("fechaString", fechaString);

        return "reservasCli";
    }
    
    @PostMapping("/nuevoclienteC")
    public String nuevocliente(@Valid @ModelAttribute Cliente cliente, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        if (result.hasErrors()) {
            model.addAttribute("cliente", cliente);
            System.out.println("Existen errores en el formulario");
            return "web";
        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        cliente.setFechaUpdate(fechString + " " + horaString);

        cliente.setHostName(check.host().getHostName());
        cliente.setIp(check.host().getHostAddress());

        clienteService.guardar(cliente);
        System.out.println("Tipo Empleado guardado con exito");
        attribute.addFlashAttribute("success", "Tipo Empleado guardado con exito!");
        
        return "redirect:/menureservaC/";
    }
    
    @PostMapping("/nuevoreservaC")
    public String nuevareserva(@Valid @ModelAttribute Reserva reserva, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        List<Cliente> cliente = clienteService.listarCliente();

        if (result.hasErrors()) {
            model.addAttribute("reserva", reserva);
            model.addAttribute("cliente", cliente);
            System.out.println("Existen errores en el formulario");
            return "web";

        }
        
        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        reserva.setFechaUpdate(fechString + " " + horaString);
        
        reserva.setHostName(check.host().getHostName());
        reserva.setIp(check.host().getHostAddress());
        
                
        reservaService.guardar(reserva);
        System.out.println("Reserva guardado con exito");
        attribute.addFlashAttribute("success", "Reserva guardado con exito!");

        return "redirect:/menureservaC/";
    }
    
    @GetMapping("/error")
    public String handleError(HttpServletRequest request){

            return "redirect:/dash";
    }


    public String getErrorPath(){

            return "/error";
    }    
}
