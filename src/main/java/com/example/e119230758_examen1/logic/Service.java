package com.example.e119230758_examen1.logic;

import com.example.e119230758_examen1.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@org.springframework.stereotype.Service
public class Service
{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FarmaciaRepository farmaciaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private PacienteMedicamentoRepository pacienteMedicamentoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario autenticar (String idUsuario, String password)
    {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario != null)
        {
            if (passwordEncoder.matches(password, usuario.getClave()))
            {
                return usuario;
            }
        }
        return null;
    }

    public Paciente buscarPaciente(String pacienteId)
    {
        return pacienteRepository.findById(pacienteId).orElse(null);
    }

    public List<Pacientemedicamento> obtenerMedicamentosPaciente (String pacienteId)
    {
        return pacienteMedicamentoRepository.findByPacienteId(pacienteId);
    }

    public Farmacia obtenerFarmaciaPorUsuario(String usuarioId) {
        return farmaciaRepository.findById(usuarioId).orElse(null);
    }

    public void registrarCompra (Integer pacienteMedicamentoId, Integer cantidad)
    {
        Pacientemedicamento pm = pacienteMedicamentoRepository.findById(pacienteMedicamentoId).orElse(null);
        if (pm != null)
        {
            int actual;
            if (pm.getDosisafavor() == null)
            {
                actual = 0;
            }
            else
            {
                actual = pm.getDosisafavor();
            }
            pm.setDosisafavor(actual+cantidad);
            pacienteMedicamentoRepository.save(pm);
        }
    }

    public String entregarRegalia (Integer pacienteMedicamentoId)
    {
        Pacientemedicamento pm = pacienteMedicamentoRepository.findById(pacienteMedicamentoId).orElse(null);
        if (pm == null)
        {
            return "Registro no encontrado";
        }

        int plan;
        if (pm.getMedicamento().getPlan() == null)
        {
            plan = 0;
        }
        else
        {
            plan = pm.getMedicamento().getPlan();
        }

        int lasquellevaAcumuladas;
        if (pm.getDosisafavor() == null)
        {
            lasquellevaAcumuladas = 0;
        }
        else
        {
            lasquellevaAcumuladas = pm.getDosisafavor();
        }

        if (lasquellevaAcumuladas < plan)
        {
            return "No hay dosis suficientes para entregarle.";
        }

        pm.setDosisafavor(lasquellevaAcumuladas-plan);
        pacienteMedicamentoRepository.save(pm);
        return null;
    }
}
