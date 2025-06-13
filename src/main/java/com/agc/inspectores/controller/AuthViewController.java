package com.agc.inspectores.controller;

import com.agc.inspectores.dto.RegisterDTO;
import com.agc.inspectores.service.AuthService;
import com.agc.inspectores.service.InspectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("inspectores", inspectorService.getAll());
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
    public String registerUserBySuperadminForm(@ModelAttribute("user") RegisterDTO registerDTO) {
        authService.registerByRole(registerDTO);
        return "redirect:/auth/dashboard";
    }


}
