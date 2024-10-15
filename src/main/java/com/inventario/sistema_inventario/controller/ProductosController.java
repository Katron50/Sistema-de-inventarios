package com.inventario.sistema_inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
        model.addAttribute("editarModal", "Editar producto");

        List<Producto> productos = repo.findByDisponibility(true);
        List<Categoria> categorias =  repoCat.findByDisponibility(true);

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categorias);

        model.addAttribute("productoAdd", new Producto());

        return "productosMenu";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Producto obtenerProductoPorId(@PathVariable Long id) {
        return repo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
    }


    @PostMapping("/add")
    public String agregarProducto(@Valid @ModelAttribute("productoAdd") Producto producto, BindingResult result) {
        
        MultipartFile image = producto.getImageFile();
        String imageName = producto.getName().trim() + ".jpg";
        
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
            }
        }

        Integer tolerance = producto.getTolerance(); // Obtener el valor directamente

        // Validar tolerancia
        if (tolerance == null || tolerance < 0) {
            producto.setTolerance(0);
        }

        // Verificar si el campo tolerancia está vacío o no es un número
        try {
            if (String.valueOf(tolerance).isBlank()) {
                producto.setTolerance(0);
            }
        } catch (NumberFormatException e) {
            producto.setTolerance(0);
            result.rejectValue("tolerance", "invalid.tolerance", "La tolerancia debe ser un número válido.");
        }

        
        
        if (result.hasErrors()) {
            System.out.println(result);
            return "productosMenu";
        }
        
        // Guardar la nueva categoria en la base de datos
        repo.save(producto);
        
        // Redirigir al menu de categorias
        return "redirect:/productos";
    }


    @PostMapping("/edit")
    public String editarCategoria(@RequestParam Long id, @Valid @ModelAttribute("producto") Producto producto, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/productos";  // Si hay errores de validación, redirige
        }
        Producto productoExistente = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));

        // Actualiza los valores de la categoría existentes
        
        

        if(!producto.getImageFile().isEmpty()){
            String uploadDir = "public/images/";
            Path oldImagePath = Paths.get(uploadDir + producto.getImage());
            MultipartFile image = producto.getImageFile();
            String imageName = producto.getName().trim() + ".jpg";
            
            
            try {
                if(producto.getImage() != null){
                    Files.delete(oldImagePath);
                    
                }
                
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }

            

            try (InputStream inputStream = image.getInputStream()){

                Files.copy(inputStream, Paths.get(uploadDir + imageName), StandardCopyOption.REPLACE_EXISTING);
                productoExistente.setImage(imageName);

            }catch (IOException e) {
                // Manejo de la excepción
                e.printStackTrace();
            }

            

        }

        productoExistente.setName(producto.getName());
        productoExistente.setDescription(producto.getDescription());
        productoExistente.setCategoria(producto.getCategoria());

        if (producto.getTolerance() >= 0){
            productoExistente.setTolerance(producto.getTolerance());

        }
        
        // Guarda los cambios
        repo.save(productoExistente);

        return "redirect:/productos";
    }

    // Método para eliminar la imagen de un producto
    @DeleteMapping("{id}/imagen_delete")  
    public ResponseEntity<?> borrarImagen(@PathVariable Long id) {
        System.out.println("ID recibido para eliminar la imagen: " + id); // Mensaje de depuración
        
        Producto productoExistente = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));

        // Verificar si el producto tiene una imagen
        if (productoExistente.getImage() != null) {
            String uploadDir = "public/images/";
            Path imagePath = Paths.get(uploadDir + productoExistente.getImage());

            try {
                // Eliminar la imagen del sistema de archivos
                Files.delete(imagePath);
                // Quitar la referencia de la imagen en el producto
                productoExistente.setImage(null);
                repo.save(productoExistente); // Guardar los cambios en la base de datos

            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la imagen: " + e.getMessage());
            }
        }

        return ResponseEntity.ok("Imagen eliminada correctamente");
    }
    
    @PostMapping("/delete")
    public String eliminarSuavemente(@RequestParam Long id) {
        Producto producto = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        
        // Marcar la categoría como no disponible
        producto.setDisponibility(false);
        repo.save(producto);
    
        return "redirect:/productos";
    }






}
