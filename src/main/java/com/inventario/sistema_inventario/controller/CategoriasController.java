package com.inventario.sistema_inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.inventario.sistema_inventario.models.Categoria;
import com.inventario.sistema_inventario.models.Producto;


@Controller
public class CategoriasController {
    
    /* https://localhost:8080/categorias */
    @GetMapping("/categorias")
    public String categoriasMenu(Model model) {
        //Titulo de la página
        model.addAttribute("title", "Categorias");
        model.addAttribute("agregarModal", "Agregar Nueva Categoria");
        
        
        //Crud Categorias
        Categoria categoria = new Categoria(1, "Escritura", "Categoria relacionada a la escritura", "19-10-2003", true);
        model.addAttribute("categorias", categoria);
        
        return "categoriasMenu";
    }

    @GetMapping("/productos")
    public String productosMenu(Model model) {
        Producto producto = new Producto(1, "KK123123", "Lapíz de escritura");
        model.addAttribute("productos", producto);
        return "productosMenu";
    }
    
    
}
