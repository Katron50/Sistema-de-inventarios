package com.inventario.sistema_inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inventario.sistema_inventario.models.Categoria;
import com.inventario.sistema_inventario.models.Producto;
import com.inventario.sistema_inventario.services.CategoriaRepository;
import com.inventario.sistema_inventario.services.ProductoRepository;


@Controller
@RequestMapping("/productos")
public class ProductosController {
    
    @Autowired
    private ProductoRepository repo;
    
    @Autowired
    private CategoriaRepository repoCat;

    @GetMapping("")
    public String productosMenu(Model model){
        model.addAttribute("title", "Productos");
        model.addAttribute("agregarModal", "Agregar nuevo producto");

        List<Producto> productos = repo.findAll();
        List<Categoria> categorias = repoCat.findAll();

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);

        model.addAttribute("productoAdd", new Producto());

        return "productosMenu";
    }
}
