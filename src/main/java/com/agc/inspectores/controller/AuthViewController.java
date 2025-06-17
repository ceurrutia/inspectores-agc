package com.agc.inspectores.controller;

import com.agc.inspectores.dto.RegisterDTO;
import com.agc.inspectores.dto.UserDTO;
import com.agc.inspectores.enums.Area;
import com.agc.inspectores.service.AuthService;
import com.agc.inspectores.service.InspectorService;
import com.agc.inspectores.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthViewController {

    @Autowired
    AuthService authService;

    @Autowired
    InspectorService inspectorService;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/dashboard-superadmin")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public String dashboardSuperadmin(Model model) {
        model.addAttribute("usuarios", userService.getAllUsers());
        return "dashboard-superadmin";
    }

    @GetMapping("/redireccion")
    public String redireccionSegunRol() {
        var auth = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication();

        var roles = auth.getAuthorities().stream()
                .map(granted -> granted.getAuthority())
                .toList();

        if (roles.contains("ROLE_SUPERADMIN")) {
            return "redirect:/auth/dashboard-superadmin";
        }

        // Si no es superadmin, va al dashboard normal
        return "redirect:/auth/dashboard";
    }

    // Dashboard con búsqueda por apellido
    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(value = "apellido", required = false) String apellido, Model model) {
        if (apellido != null && !apellido.trim().isEmpty()) {
            model.addAttribute("inspectores", inspectorService.buscarPorApellido(apellido.trim()));
        } else {
            model.addAttribute("inspectores", inspectorService.getAll());
        }
        model.addAttribute("areas", Area.values());
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

        //ok,aldashboard
        return "redirect:/auth/dashboard-superadmin";
    }

    //Metodos para editar o eliminar usuarios para que los levante thymeleaf

    @PostMapping("/admin/edit/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public String editarUsuario(@PathVariable Long id,
                                @ModelAttribute UserDTO userDTO,
                                RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(id, userDTO);
            redirectAttributes.addFlashAttribute("success", "Usuario actualizado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar: " + e.getMessage());
        }
        return "redirect:/auth/dashboard-superadmin";
    }

    @PostMapping("/admin/delete/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public String eliminarUsuario(@PathVariable Long id,
                                  RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("success", "Usuario eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/auth/dashboard-superadmin";
    }
}
