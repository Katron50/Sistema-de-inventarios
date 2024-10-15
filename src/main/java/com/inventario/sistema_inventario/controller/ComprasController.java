package com.inventario.sistema_inventario.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inventario.sistema_inventario.models.Compra;
import com.inventario.sistema_inventario.models.Producto;
import com.inventario.sistema_inventario.services.CompraRepository;
import com.inventario.sistema_inventario.services.ProductoRepository;

@Controller
@RequestMapping("/compras")
public class ComprasController {
    
    @Autowired
    private CompraRepository repo;

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

        return "comprasMenu";
    }

}
