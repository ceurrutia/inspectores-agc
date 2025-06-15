package com.agc.inspectores.controller;

import com.agc.inspectores.dto.RegisterDTO;
import com.agc.inspectores.service.AuthService;
import com.agc.inspectores.service.InspectorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthViewController {

    @Autowired
    AuthService authService;

    @Autowired
    InspectorService inspectorService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // Dashboard con búsqueda por apellido
    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(value = "apellido", required = false) String apellido, Model model) {
        if (apellido != null && !apellido.trim().isEmpty()) {
            model.addAttribute("inspectores", inspectorService.buscarPorApellido(apellido.trim()));
        } else {
            model.addAttribute("inspectores", inspectorService.getAll());
        }
        return "dashboard";
    }
    

    // Mostrar formulario para crear ADMIN (solo SUPERADMIN)
    @GetMapping("/admin/create")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public String showAdminRegistrationForm(Model model) {
        model.addAttribute("user", new RegisterDTO());
        return "register-admin";
    }

    // Procesar formcrear ADMIN (solo para SUPERADMIN)
    @PostMapping("/admin/create")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public String registerUserBySuperadminForm(@ModelAttribute("user") RegisterDTO registerDTO,
                                               Model model) {

        String resultado = authService.registerByRole(registerDTO);

        // Si hubo error, mostrarlo en la misma vista
        if (!resultado.startsWith("Usuario")) {
            model.addAttribute("error", resultado); // pasamos el mensaje al HTML
            model.addAttribute("user", registerDTO); // mantiene los datos del form
            return "register-admin"; // vuelve al formulario
        }

        // Si todo salió bien, redirige al dashboard
        return "redirect:/auth/dashboard";
    }



}
