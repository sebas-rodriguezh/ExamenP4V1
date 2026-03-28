package com.example.e119230758_examen1.data;

import com.example.e119230758_examen1.logic.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner
{
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository UsuarioRepository;

    @Override
    public void run(String... args) throws Exception
    {
        cargarUsuarios();
//        cargarCategorias();
//        cargarProductos();
    }

    private void cargarUsuarios() {
        Usuario usuario1 = new Usuario();
        usuario1.setId("juan");
        usuario1.setClave(passwordEncoder.encode("111"));
        usuario1.setRol("Usuario");
        UsuarioRepository.save(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setId("ana");
        usuario2.setClave(passwordEncoder.encode("222"));
        usuario2.setRol("Usuario");
        UsuarioRepository.save(usuario2);
    }
}
