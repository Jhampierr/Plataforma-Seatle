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
import pe.com.seatle.model.Cliente;
import pe.com.seatle.model.Empleado;
import pe.com.seatle.model.Oferta;
import pe.com.seatle.model.Pedido;
import pe.com.seatle.model.Producto;
import pe.com.seatle.servicio.ClienteService;
import pe.com.seatle.servicio.EmpleadoService;
import pe.com.seatle.servicio.OfertaService;
import pe.com.seatle.servicio.PedidoService;
import pe.com.seatle.servicio.ProductoService;
import pe.com.seatle.servicio.ReservaService;

@Controller
@Slf4j
public class ControladorPedido {

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

    @Autowired
    private ReservaService reservaService;

    String fechaString = LocalDate.now().toString();

    @GetMapping("/pedido")
    public String pedido(Model model) {
        var pedido = pedidoService.listarPedido();
        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("pedido", pedido);
        model.addAttribute("fechaString", fechaString);

        return "pedidoSEL";
    }

    @GetMapping("/dash")
    public String dash(Model model) {
        var pedido = pedidoService.listarPedido();
        var producto = productoService.listarProducto();
        var reserva = reservaService.listarReserva();
                
        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("pedido", pedido);
        model.addAttribute("producto", producto);
        model.addAttribute("reserva", reserva);
        
        var ingresoDia = 0D;
        var nuevo = 0;
        var pendiente = 0;
        var finalizado = 0;
        for (var p : pedido) {
            if (p.getFecha().equals(fechaString)) {
                if (p.getEstado().equals("Finalizado")) {
                    ingresoDia += p.getTotal();
                    finalizado += 1;
                }
                if (p.getEstado().equals("Nuevo")) {
                    nuevo += 1;
                }
                if (p.getEstado().equals("Pendiente")) {
                    pendiente += 1;
                }
            }
        }
        model.addAttribute("ingresoDia", ingresoDia);
        model.addAttribute("nuevo", nuevo);
        model.addAttribute("pendiente", pendiente);
        model.addAttribute("finalizado", finalizado);
        model.addAttribute("fechaString", fechaString);

        return "index";
    }

    @GetMapping("/agregarpedido")
    public String agregar(Model model) {
        Pedido pedido = new Pedido();
        List<Cliente> cliente = clienteService.listarCliente();
        List<Empleado> empleado = empleadoService.listarEmpleado();
        List<Producto> producto = productoService.listarProducto();
        List<Oferta> oferta = ofertaService.listarOferta();

        model.addAttribute("pedido", pedido);
        model.addAttribute("cliente", cliente);
        model.addAttribute("empleado", empleado);
        model.addAttribute("producto", producto);
        model.addAttribute("oferta", oferta);
        model.addAttribute("fechaString", fechaString);

        return "pedidoUPD";
    }

    @PostMapping("/guardarpedido")
    public String guardar(@Valid @ModelAttribute Pedido pedido, BindingResult result,
            Model model, CheckIP check, RedirectAttributes attribute) {

        List<Cliente> cliente = clienteService.listarCliente();
        List<Empleado> empleado = empleadoService.listarEmpleado();
        List<Producto> producto = productoService.listarProducto();
        List<Oferta> oferta = ofertaService.listarOferta();

        if (result.hasErrors()) {
            model.addAttribute("pedido", pedido);
            model.addAttribute("cliente", cliente);
            model.addAttribute("empleado", empleado);
            model.addAttribute("producto", producto);
            model.addAttribute("oferta", oferta);
            System.out.println("Existen errores en el formulario");

            return "pedidoUPD";

        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        pedido.setFechaUpdate(fechString + " " + horaString);

        pedido.setHostName(check.host().getHostName());
        pedido.setIp(check.host().getHostAddress());

        Double p1 = pedido.getProducto().getPrecio();
        Integer cp1 = pedido.getCantidadProducto();

        Double o1 = pedido.getOferta().getPrecioOferta();
        Integer co1 = pedido.getCantidadOferta();

        if (cp1 == null && co1 == null) {
            pedido.setTotal(0D);
        } else if (cp1 == null) {
            cp1 = 0;
            pedido.setTotal(p1 + (co1 * o1));
        } else if (co1 == null) {
            co1 = 0;
            pedido.setTotal((cp1 * p1) + o1);
        } else {
            pedido.setTotal((cp1 * p1) + (co1 * o1));
        }

        pedidoService.guardar(pedido);
        System.out.println("Pedido guardado con exito");
        attribute.addFlashAttribute("success", "Pedido guardado con exito!");

        return "redirect:/pedido/";
    }

    @GetMapping("/editarpedido/{idPedido}")
    public String editar(@PathVariable("idPedido") Long idPedido,
            Model model, RedirectAttributes attribute) {

        Pedido pedido = null;

        if (idPedido > 0) {
            pedido = pedidoService.encontrarPedido(idPedido);
            if (pedido == null) {
                System.out.println("Error: El ID del pedido no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del pedido no existe!");
                return "pedidoUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Pedido");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del pedido");
            return "pedidoUPD";
        }

        List<Cliente> cliente = clienteService.listarCliente();
        List<Empleado> empleado = empleadoService.listarEmpleado();
        List<Producto> producto = productoService.listarProducto();
        List<Oferta> oferta = ofertaService.listarOferta();

        model.addAttribute("pedido", pedido);
        model.addAttribute("cliente", cliente);
        model.addAttribute("empleado", empleado);
        model.addAttribute("producto", producto);
        model.addAttribute("oferta", oferta);
        model.addAttribute("fechaString", fechaString);

        return "pedidoUPD";
    }

    @GetMapping("/eliminarpedido/{idPedido}")
    public String eliminar(@PathVariable("idPedido") Long idPedido,
            RedirectAttributes attribute) {

        Pedido pedido = null;

        if (idPedido > 0) {
            pedido = pedidoService.encontrarPedido(idPedido);
            if (pedido == null) {
                System.out.println("Error: El ID del pedido no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del pedido no existe!");
                return "pedidoUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Pedido");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del pedido");
            return "pedidoUPD";
        }

        pedidoService.eliminar(idPedido);
        System.out.println("Pedido eliminado con exito");
        attribute.addFlashAttribute("warning", "pedido eliminado con Exito!");
        return "redirect:/pedido/";
    }

}
