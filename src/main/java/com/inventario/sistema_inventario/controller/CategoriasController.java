package com.inventario.sistema_inventario.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inventario.sistema_inventario.models.Categoria;
import com.inventario.sistema_inventario.services.CategoriaRepository;
import com.inventario.sistema_inventario.services.ProductoRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/categorias")
public class CategoriasController {

    @Autowired
    private CategoriaRepository repo;

    @Autowired
    private ProductoRepository repoProducts;
    

    //Mostrar Categorias
    /* https://localhost:8080/categorias */
    @GetMapping("")
    public String categoriasMenu(Model model) {
        //Titulo de la página
        model.addAttribute("title", "Categorias");
        model.addAttribute("agregarModal", "Añadir Categoria");
        model.addAttribute("editarModal", "Editar Categoria");
        
        //Lista de categorias
        List<Categoria> categorias = repo.findByDisponibility(true);
        model.addAttribute("categorias", categorias);

        // Contar productos por cada categoría y almacenarlo en el modelo
        Map<Long, Integer> productosPorCategoria = new HashMap<>();
        for (Categoria categoria : categorias) {
            int count = repoProducts.countByDisponibilityAndCategoriaId(true, categoria.getId());
            productosPorCategoria.put(categoria.getId(), count);
        }
        model.addAttribute("productosPorCategoria", productosPorCategoria);

        model.addAttribute("categoriaAdd", new Categoria());

        return "categoriasMenu";
    }
                            
    @PostMapping("/add")
    public String agregarCategoria(@Valid @ModelAttribute("categoriaAdd") Categoria categoria, BindingResult result) {
        //Ver si ya existe la categoria
        if (repo.existsByName(categoria.getName())) {
            result.rejectValue("name", "error.categoriaAdd", "La categoría ya existe.");
        }

        if (result.hasErrors()) {
            return "";
        }
        
        // Guardar la nueva categoria en la base de datos
        repo.save(categoria);

        // Redirigir al menu de categorias
        return "redirect:/categorias";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Categoria obtenerCategoriaPorId(@PathVariable Long id) {
        return repo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada: " + id));
    }
    
    
    @PostMapping("/edit")
    public String editarCategoria(@RequestParam Long id, @Valid @ModelAttribute("categoria") Categoria categoria, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/categorias";  // Si hay errores de validación, redirige
        }
        Categoria categoriaExistente = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada: " + id));

        // Actualiza los valores de la categoría existente
        categoriaExistente.setName(categoria.getName());
        categoriaExistente.setDescription(categoria.getDescription());

        // Guarda los cambios
        repo.save(categoriaExistente);

        return "redirect:/categorias";
    }

    @PostMapping("/delete")
    public String eliminarSuavemente(@RequestParam Long id) {
        Categoria categoria = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
        
        // Marcar la categoría como no disponible
        categoria.setDisponibility(false);
        repo.save(categoria);
    
        return "redirect:/categorias";
    }
    
    
}
