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
public class ControladorReserva {

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

    @GetMapping("/reserva")
    public String reserva(Model model) {
        var reserva = reservaService.listarReserva();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("reserva", reserva);
        model.addAttribute("fechaString", fechaString);
        
        return "reservaSEL";
    }
        
    @GetMapping("/agregarreserva")
    public String agregar(Model model) {
        Reserva reserva = new Reserva();
        List<Pedido> pedido = pedidoService.listarPedido();
        List<Cliente> cliente = clienteService.listarCliente();
        List<Empleado> empleado = empleadoService.listarEmpleado();
        List<Producto> producto = productoService.listarProducto();
        List<Oferta> oferta = ofertaService.listarOferta();

        model.addAttribute("reserva", reserva);
        model.addAttribute("pedido", pedido);
        model.addAttribute("cliente", cliente);
        model.addAttribute("empleado", empleado);
        model.addAttribute("producto", producto);
        model.addAttribute("oferta", oferta);
        model.addAttribute("fechaString", fechaString);

        return "reservaUPD";
    }

    @PostMapping("/guardarreserva")
    public String guardar(@Valid @ModelAttribute Reserva reserva, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {
        
        List<Pedido> pedido = pedidoService.listarPedido();
        List<Cliente> cliente = clienteService.listarCliente();
        List<Empleado> empleado = empleadoService.listarEmpleado();
        List<Producto> producto = productoService.listarProducto();
        List<Oferta> oferta = ofertaService.listarOferta();

        if (result.hasErrors()) {
            model.addAttribute("reserva", reserva);
            model.addAttribute("pedido", pedido);
            model.addAttribute("cliente", cliente);
            model.addAttribute("empleado", empleado);
            model.addAttribute("producto", producto);
            model.addAttribute("oferta", oferta);
            System.out.println("Existen errores en el formulario");

            return "reservaUPD";

        }
        
        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        reserva.setFechaUpdate(fechString + " " + horaString);
        
        reserva.setHostName(check.host().getHostName());
        reserva.setIp(check.host().getHostAddress());
        
                
        reservaService.guardar(reserva);
        System.out.println("Reserva guardado con exito");
        attribute.addFlashAttribute("success", "Reserva guardado con exito!");

        return "redirect:/reserva/";
    }
    /*
    @GetMapping("/detallepedido/{idPedido}")
    public String detalle(@PathVariable("idPedido") Long idPedido,
            Model model, RedirectAttributes attribute) {

        Pedido pedido = null;

        if (idPedido > 0) {
            pedido = pedidoService.encontrarPedido(idPedido);
            if (pedido == null) {
                attribute.addFlashAttribute("error", "ATENCION: El ID del pedido no existe!");
                return "redirect:/pedido/";
            }
        } else {
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del pedido");
            return "redirect:/pedido/";
        }

        model.addAttribute("pedido", pedido);

        return "pedidoDetalle";
    }*/

    @GetMapping("/editarreserva/{idReserva}")
    public String editar(@PathVariable("idReserva") Long idReserva,
            Model model, RedirectAttributes attribute) {

        Reserva reserva = null;

        if (idReserva > 0) {
            reserva = reservaService.encontrarReserva(idReserva);
            if (reserva == null) {
                System.out.println("Error: El ID del reserva no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del reserva no existe!");
                return "reservaUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Reserva");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del reserva");
            return "reservaUPD";
        }
        
        List<Pedido> pedido = pedidoService.listarPedido();
        List<Cliente> cliente = clienteService.listarCliente();
        List<Empleado> empleado = empleadoService.listarEmpleado();
        List<Producto> producto = productoService.listarProducto();
        List<Oferta> oferta = ofertaService.listarOferta();

        model.addAttribute("reserva", reserva);
        model.addAttribute("pedido", pedido);
        model.addAttribute("cliente", cliente);
        model.addAttribute("empleado", empleado);
        model.addAttribute("producto", producto);
        model.addAttribute("oferta", oferta);
        model.addAttribute("fechaString", fechaString);

        return "reservaUPD";
    }

    @GetMapping("/eliminarreserva/{idReserva}")
    public String eliminar(@PathVariable("idReserva") Long idReserva,
            RedirectAttributes attribute) {

        Reserva reserva = null;

        if (idReserva > 0) {
            reserva = reservaService.encontrarReserva(idReserva);
            if (reserva == null) {
                System.out.println("Error: El ID del reserva no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del reserva no existe!");
                return "reservaUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Reserva");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del reserva");
            return "reservaUPD";
        }

        reservaService.eliminar(idReserva);
        System.out.println("Reserva eliminado con exito");
        attribute.addFlashAttribute("warning", "reserva eliminado con Exito!");
        return "redirect:/reserva/";
    }

}
