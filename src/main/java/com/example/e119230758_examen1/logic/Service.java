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

}
