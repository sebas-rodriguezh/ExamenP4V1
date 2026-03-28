package com.example.e119230758_examen1.data;

import com.example.e119230758_examen1.logic.Farmacia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmaciaRepository extends JpaRepository<Farmacia,String> {
    Farmacia findByUsuarioId(String idUsuario);
}
