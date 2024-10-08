package com.inventario.sistema_inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.inventario.sistema_inventario.models.Categoria;
import com.inventario.sistema_inventario.models.Producto;
import com.inventario.sistema_inventario.services.CategoriaRepository;
import com.inventario.sistema_inventario.services.ProductoRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

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
        List<Categoria> categorias =  repoCat.findByDisponibility(true);

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);

        model.addAttribute("productoAdd", new Producto());

        return "productosMenu";
    }


    @PostMapping("/add")
    public String agregarProducto(@Valid @ModelAttribute("productoAdd") Producto producto, BindingResult result) {

        if (result.hasErrors()) {
            return "productosMenu";
        }
        
        MultipartFile image = producto.getImageFile();
        String imageName = producto.getName() + ".jpg";

        if (image != null && !image.isEmpty()) {
            try {
                String dirImages = "public/images/";
                Path dirImPath = Paths.get(dirImages);

                if(!Files.exists(dirImPath)){
                    Files.createDirectories(dirImPath);
                }

                try (InputStream inputStream = image.getInputStream()){
                    Files.copy(inputStream, Paths.get(dirImages + imageName), StandardCopyOption.REPLACE_EXISTING);
                    producto.setImage(imageName);
                }
               
            } catch (IOException e) {
                System.out.println("Error al guardar la imagen: " + e.getMessage());
                // Manejar error de manera adecuada
            }
        }


        // Guardar la nueva categoria en la base de datos
        repo.save(producto);

        // Redirigir al menu de categorias
        return "redirect:/productos";
    }
    






}
