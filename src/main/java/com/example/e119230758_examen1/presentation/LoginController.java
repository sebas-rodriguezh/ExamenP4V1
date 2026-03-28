package com.example.e119230758_examen1.presentation;

import jakarta.servlet.http.HttpSession;
import com.example.e119230758_examen1.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private Service service;

    // GET / — página de inicio con formulario de login
    @GetMapping("/")
    public String index(HttpSession session, Model model)
    {
        // Si ya hay sesión activa, redirige al catálogo de productos
        if (session.getAttribute("usuarioId") != null)
        {
            return "redirect:/productos";
        }
        return "/index";
    }

    // GET /login — mismo que GET /, muestra el formulario
    @GetMapping("/login")
    public String mostrarLogin(HttpSession session)
    {
        if (session.getAttribute("usuarioId") != null)
        {
//            return "redirect:/productos";
        }
        return "/index";
    }

    // POST /login — procesa las credenciales
    @PostMapping("/login")
    public String procesarLogin(HttpSession session, Model model,
                                @RequestParam String usuarioId,
                                @RequestParam String password)
    {

        if (usuarioId == null || usuarioId.trim().isEmpty() || password == null || password.trim().isEmpty())
        {
            model.addAttribute("error", "Usuario y contraseña son requeridos");
            return "/index";
        }

        Usuario usuario = service.autenticar(usuarioId, password);

        if (usuario != null)
        {
            session.setAttribute("usuarioId", usuario.getId());
            session.setAttribute("usuarioNombre", usuario.getRol());
            return "redirect:/productos";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "/index";
        }
    }

    // GET /logout — invalida la sesión y vuelve al inicio
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}