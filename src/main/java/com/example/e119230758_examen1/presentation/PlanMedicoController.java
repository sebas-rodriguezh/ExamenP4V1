package com.example.e119230758_examen1.presentation;

import com.example.e119230758_examen1.logic.Farmacia;
import com.example.e119230758_examen1.logic.Paciente;
import com.example.e119230758_examen1.logic.Pacientemedicamento;
import jakarta.servlet.http.HttpSession;
import com.example.e119230758_examen1.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PlanMedicoController
{
    @Autowired Service service;

    @GetMapping("/presentation/planMedico/planMedico")
    public String show(Model model, HttpSession session)
    {
        String usuarioId = session.getAttribute("usuarioId").toString();
        if (usuarioId == null)
        {
            return "redirect:/login";
        }
        Farmacia farmacia = service.obtenerFarmaciaPorUsuario(usuarioId);
        model.addAttribute("farmacia", farmacia);
        return "presentation/planMedico/planMedico";
    }

    @GetMapping("/presentation/planMedico/refrescar")
    public String refrescar(HttpSession session, Model model, @RequestParam(required = false) String pacienteId)
    {
        String usuarioId = session.getAttribute("usuarioId").toString();
        if (usuarioId == null)
        {
            return "redirect:/login";
        }

        Farmacia farmacia = service.obtenerFarmaciaPorUsuario(usuarioId);
        model.addAttribute("farmacia", farmacia);

        if (pacienteId != null && !pacienteId.trim().isEmpty())
        {
            Paciente paciente = service.buscarPaciente(pacienteId.trim());
            model.addAttribute("pacienteId", pacienteId.trim());

            if (paciente != null)
            {
                List<Pacientemedicamento> medicamentos = service.obtenerMedicamentosPaciente(paciente.getId());
                model.addAttribute("paciente", paciente);
                model.addAttribute("medicamentos", medicamentos);
            }
            else
            {
                model.addAttribute("errorPaciente", "Paciente no encontrado");
            }

        }
        return "presentation/planMedico/planMedico";
    }

    @PostMapping("/presentation/planMedico/medicamento/registrar")
    public String registrar (HttpSession session, @RequestParam Integer cantidad, @RequestParam String pacienteId, @RequestParam Integer pmId)
    {
        String usuarioId = session.getAttribute("usuarioId").toString();
        if (usuarioId == null)
        {
            return "redirect:/login";
        }
        service.registrarCompra(pmId, cantidad);
        return "redirect:/presentation/planMedico/refrescar?pacienteId=" + pacienteId;

    }

    @PostMapping("/presentation/planMedico/medicamento/entregar")
    public String entregar (HttpSession session, Model model, @RequestParam Integer pmId,  @RequestParam String pacienteId)
    {
        String usuarioId = session.getAttribute("usuarioId").toString();
        if (usuarioId == null)
        {
            return "redirect:/login";
        }
        String error = service.entregarRegalia(pmId);

        if (error != null)
        {
            Farmacia farmacia = service.obtenerFarmaciaPorUsuario(usuarioId);
            Paciente paciente = service.buscarPaciente(pacienteId);
            List<Pacientemedicamento> medicamentos = service.obtenerMedicamentosPaciente(pacienteId);

            model.addAttribute("farmacia", farmacia);
            model.addAttribute("pacienteId", pacienteId);
            model.addAttribute("paciente", paciente);
            model.addAttribute("medicamentos", medicamentos);
            model.addAttribute("errorEntrega", error);
            return "presentation/planMedico/planMedico";
        }
        return "redirect:/presentation/planMedico/refrescar?pacienteId=" + pacienteId;
    }
}
