
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
import pe.com.sgv.model.CatProducto;
import pe.com.sgv.servicio.CatProductoService;

@Controller
@Slf4j
public class ControladorCatProducto {
   
    @Autowired
    private CatProductoService catProductoService;
    
    String fechaString = LocalDate.now().toString();
    
    @GetMapping("/catProducto")
    public String catProducto(Model model){
        var catProducto = catProductoService.listarCatProducto();
        
        log.info("Ejecutando el controlador Spring MVC");
        model.addAttribute("catProducto", catProducto);
        model.addAttribute("fechaString", fechaString);
        
        return "catProductoSEL";
    }
    
    @GetMapping("/agregarcatProducto")
    public String agregarcatProducto(Model model){
        CatProducto catProducto = new CatProducto();
        model.addAttribute("catProducto", catProducto);
        model.addAttribute("fechaString", fechaString);
        
        return "catProductoUPD";
    }
    
    @PostMapping("/guardarcatProducto")
    public String guardarcatProducto(@Valid @ModelAttribute CatProducto catProducto, BindingResult result, 
            Model model, CheckIP check, RedirectAttributes attribute) {
        if(result.hasErrors()){
            model.addAttribute("catProducto", catProducto);
            System.out.println("Existen errores en el formulario");
            return "catProductoUPD";
        }
        
        String fechString = LocalDate.now().toString();
        String horaString = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM));
        catProducto.setFechaUpdate(fechString + " " + horaString);
        
        catProducto.setHostName(check.host().getHostName());
        catProducto.setIp(check.host().getHostAddress());
        
        catProductoService.guardar(catProducto);
        System.out.println("Categoria Producto guardado con exito");
        attribute.addFlashAttribute("success", "Categoria Producto guardado con exito!");
        
        return "redirect:/catProducto/";
    }
    
    @GetMapping("/editarcatProducto/{idCatProducto}")
    public String editarcatProducto(@PathVariable("idCatProducto") Long idCatProducto, 
            Model model, RedirectAttributes attribute){
        
        CatProducto catProducto = null;
        
        if (idCatProducto > 0) {
            catProducto = catProductoService.encontrarCatProducto(idCatProducto);
            if (catProducto == null) {
                System.out.println("Error: El ID del catProducto no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del catProducto no existe!");
                return "catProductoUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del catProducto");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del catProducto");
            return "catProductoUPD";
        }
        
       model.addAttribute("catProducto", catProducto);
       model.addAttribute("fechaString", fechaString);
       
       return "catProductoUPD";
    }
    
    @GetMapping("/eliminarcatProducto/{idCatProducto}")
    public String eliminarcatProducto(@PathVariable("idCatProducto") Long idCatProducto, 
            Model model, RedirectAttributes attribute){
        
        CatProducto catProducto = null;
        
        if (idCatProducto > 0) {
            catProducto = catProductoService.encontrarCatProducto(idCatProducto);
            if (catProducto == null) {
                System.out.println("Error: El ID del catProducto no existe!");
                attribute.addFlashAttribute("error", "ATENCION: El ID del catProducto no existe!");
                return "catProductoUPD";
            }
        } else {
            System.out.println("Error: Error con el ID del catProducto");
            attribute.addFlashAttribute("error", "ATENCION: Error con el ID del catProducto");
            return "catProductoUPD";
        }
        
        catProductoService.eliminar(idCatProducto);
        System.out.println("CatProducto eliminado con exito");
        attribute.addFlashAttribute("warning", "CatProducto eliminado con Exito!");
        
        return "redirect:/catProducto/";
    }
    
}
