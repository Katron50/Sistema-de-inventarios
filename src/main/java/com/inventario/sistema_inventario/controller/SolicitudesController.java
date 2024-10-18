package com.inventario.sistema_inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inventario.sistema_inventario.models.Producto;
import com.inventario.sistema_inventario.models.Solicitud;
import com.inventario.sistema_inventario.models.SolicitudProducto;
import com.inventario.sistema_inventario.services.ProductoRepository;
import com.inventario.sistema_inventario.services.SolicitudProductoRepository;
import com.inventario.sistema_inventario.services.SolicitudRepository;

import jakarta.validation.Valid;



@Controller
@RequestMapping("/solicitudes")
public class SolicitudesController {

    @Autowired
    private SolicitudRepository repo;

    @Autowired
    private SolicitudProductoRepository repoSoliProduct;

    @Autowired
    private ProductoRepository repoProducts;
    
    @GetMapping("")
    public String solicitudesMenu(Model model) {
        model.addAttribute("title", "Solicitudes");
        model.addAttribute("solicitarModal", "Solicitar Material");

        List<Producto> productos = repoProducts.findByDisponibility(true);

        model.addAttribute("productos", productos);


        model.addAttribute("solicitudAdd", new SolicitudProducto());


        return "solicitudesMenu";
    }

    @PostMapping("/add")
    public String realizarSolicitud(@Valid @ModelAttribute("solicitudAdd") SolicitudProducto solicitudProducto, BindingResult result){


        
        Solicitud solicitud = new Solicitud();

        solicitud.setIdUsuarioAprobacion(null);
        solicitud.setDateEntrega(null);
        solicitud.setDateTermino(null);


        repoSoliProduct.save(solicitudProducto);
        return "redirect/solicitudes";
    }
    

}
