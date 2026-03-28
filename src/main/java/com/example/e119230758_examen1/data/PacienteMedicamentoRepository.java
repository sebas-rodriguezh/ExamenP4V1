package com.example.e119230758_examen1.data;


import com.example.e119230758_examen1.logic.Pacientemedicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteMedicamentoRepository extends JpaRepository<Pacientemedicamento, Integer> {
    List<Pacientemedicamento> findByPacienteId(String pacienteId);
}
