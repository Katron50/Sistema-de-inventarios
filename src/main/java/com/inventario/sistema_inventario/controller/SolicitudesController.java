package com.inventario.sistema_inventario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class SolicitudesController {
    
    @GetMapping("/solicitud")
    public String SolicitudesMenu(Model model) {
        model.addAttribute("message", "helloWorld");
        return "solicitudesMenu";
    }
    

}
