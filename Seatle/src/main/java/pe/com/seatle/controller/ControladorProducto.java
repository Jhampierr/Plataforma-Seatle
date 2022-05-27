package pe.com.seatle.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.com.seatle.util.CheckIP;
import pe.com.seatle.model.Grado;
import pe.com.seatle.model.Producto;
import pe.com.seatle.model.TipoProducto;
import pe.com.seatle.servicio.ProductoService;
import pe.com.seatle.servicio.TipoProductoService;
import pe.com.seatle.servicio.AlumnoService;

@Controller
@Slf4j
public class ControladorProducto {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private AlumnoService catProductoService;
    
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/producto")
    public String producto(Model model) {
        var producto = productoService.listarProducto();

        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("producto", producto);
        model.addAttribute("fechaString", fechaString);
        
        return "productoSEL";
    }

    @GetMapping("/agregarproducto")
    public String agregar(Model model) {
        Producto producto = new Producto();
        List<TipoProducto> tipoProd = tipoProductoService.listarTipoProducto();
        List<Grado> catProd = catProductoService.listarCatProducto();

        model.addAttribute("producto", producto);
        model.addAttribute("tipoProd", tipoProd);
        model.addAttribute("catProd", catProd);
        model.addAttribute("fechaString", fechaString);

        return "productoUPD";
    }

    @PostMapping("/guardarproducto")
    public String guardar(@Valid @ModelAttribute Producto producto, BindingResult result,
            Model model, @RequestParam("file") MultipartFile imagen, CheckIP check,
            RedirectAttributes attribute) {

        List<TipoProducto> tipoProd = tipoProductoService.listarTipoProducto();
        List<Grado> catProd = catProductoService.listarCatProducto();

        if (result.hasErrors()) {
            model.addAttribute("producto", producto);
            model.addAttribute("tipoProd", tipoProd);
            model.addAttribute("catProd", catProd);

            return "productoUPD";

        }
        if (!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources//static/images");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

            try {
                byte[] byteImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, byteImg);
                
                producto.setImagen(imagen.getOriginalFilename());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        producto.setFechaUpdate(fechString + " " + horaString);

        producto.setHostName(check.host().getHostName());
        producto.setIp(check.host().getHostAddress());

        productoService.guardar(producto);
        System.out.println("Producto guardado con exito");
        attribute.addFlashAttribute("success", "Producto guardado con exito!");

        return "redirect:/producto/";
    }

    @GetMapping("/detalleproducto/{idProducto}")
    public String detalle(@PathVariable("idProducto") Long idProducto,
            Model model, RedirectAttributes attribute) {

        Producto producto = null;

        if (idProducto > 0) {
            producto = productoService.encontrarProducto(idProducto);
            if (producto == null) {
                attribute.addFlashAttribute("error", "ATENCION: El ID del producto no existe!");
                return "redirect:/producto/";
            }
        } else {
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del producto");
            return "redirect:/producto/";
        }

        model.addAttribute("producto", producto);
        model.addAttribute("fechaString", fechaString);
        
        return "productoDetalle";
    }
     
    @GetMapping("/editarproducto/{idProducto}")
    public String editar(@PathVariable("idProducto") Long idProducto,
            Model model, RedirectAttributes attribute) {

        Producto producto = null;

        if (idProducto > 0) {
            producto = productoService.encontrarProducto(idProducto);
            if (producto == null) {
                System.out.println("Error: El ID del producto no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del producto no existe!");
                return "productoUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Producto");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del producto");
            return "productoUPD";
        }

        List<TipoProducto> tipoProd = tipoProductoService.listarTipoProducto();
        List<Grado> catProd = catProductoService.listarCatProducto();

        model.addAttribute("producto", producto);
        model.addAttribute("tipoProd", tipoProd);
        model.addAttribute("catProd", catProd);
        model.addAttribute("fechaString", fechaString);
        
        return "productoUPD";
    }

    @GetMapping("/eliminarproducto/{idProducto}")
    public String eliminar(@PathVariable("idProducto") Long idProducto,
            RedirectAttributes attribute) {
        
         Producto producto = null;

        if (idProducto > 0) {
            producto = productoService.encontrarProducto(idProducto);
            if (producto == null) {
                System.out.println("Error: El ID del producto no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del producto no existe!");
                return "productoUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del Producto");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del producto");
            return "productoUPD";
        }
        
        productoService.eliminar(idProducto);
        System.out.println("Producto eliminado con exito");
        attribute.addFlashAttribute("warning", "producto eliminado con Exito!");
        return "redirect:/producto/";
    }

}
