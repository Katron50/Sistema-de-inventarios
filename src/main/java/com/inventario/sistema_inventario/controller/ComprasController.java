package com.inventario.sistema_inventario.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inventario.sistema_inventario.models.Categoria;
import com.inventario.sistema_inventario.models.Comentario;
import com.inventario.sistema_inventario.models.Compra;
import com.inventario.sistema_inventario.models.Producto;
import com.inventario.sistema_inventario.services.ComentarioRepository;
import com.inventario.sistema_inventario.services.CompraRepository;
import com.inventario.sistema_inventario.services.ProductoRepository;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/compras")
public class ComprasController {
    
    @Autowired
    private CompraRepository repo;

    @Autowired
    private ComentarioRepository repoComent;

    @Autowired
    private ProductoRepository repoProducts;

    @GetMapping("")
    public String compraMenu(Model model){
        model.addAttribute("title", "Compras");
        model.addAttribute("agregarModal", "Agregar nueva compra");
        model.addAttribute("editarModal", "Editar compra");

        List<Compra> compras = repo.findByDisponibility(true);
        List<Producto> productos = repoProducts.findByDisponibility(true);
        
        model.addAttribute("compras", compras);
        model.addAttribute("productos", productos);

        model.addAttribute("compraAdd", new Compra());
        model.addAttribute("comentarioAdd", new Comentario());

        return "comprasMenu";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Compra obtenerCompraPorId(@PathVariable Long id) {
        return repo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Compra no encontrada: " + id));
    }

    @GetMapping("/comentarios/{id}")
    @ResponseBody
    public List<Comentario> obtenerComentariosDeCompra(@PathVariable Long id) {
        return repoComent.findByCompra_Id(id)
        .orElseThrow(() -> new IllegalArgumentException("Compra no encontrada: " + id));
    }
    

    @PostMapping("/add")
    public String agregarCompra(@Valid @ModelAttribute("compraAdd") Compra compra, @ModelAttribute("comentarioAdd") Comentario comentario, BindingResult result){

        if (compra.getFechaCompra().isAfter(LocalDate.now())) {
            result.rejectValue("fechaCompra", "error.compra", "La fecha de compra no puede ser en el futuro.");
        }

        if (result.hasErrors()) {
            return "comprasMenu";
        }


        repo.save(compra);
        //Para no guardar comentarios vacios
        if (!comentario.getComentario().isEmpty()){
            comentario.setCompra(compra);
            repoComent.save(comentario);
        }

        return "redirect:/compras";

    }

    @PostMapping("/edit")
    public String editarC(@RequestParam Long id, @Valid @ModelAttribute("compra") Compra compra, @ModelAttribute("comentarioAdd") Comentario comentario, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/compras";  // Si hay errores de validación, redirige
        }
        Compra compraExistente = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Compra no encontrada: " + id));

        // Actualiza los valores de la categoría existente
        compraExistente.setProducto(compra.getProducto());
        compraExistente.setCantidadComprada(compra.getCantidadComprada());
        compraExistente.setCantidadActual(compra.getCantidadActual());
        compraExistente.setCostoCompra(compra.getCostoCompra());
        compraExistente.setFechaCompra(compra.getFechaCompra());

        // Guarda los cambios
        repo.save(compraExistente);
        if (!comentario.getComentario().isEmpty()){
            comentario.setCompra(compra);
            repoComent.save(comentario);
        }
        

        return "redirect:/compras";
    }

}
