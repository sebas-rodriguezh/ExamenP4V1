package com.example.e119230758_examen1.data;

import com.example.e119230758_examen1.logic.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, String> {

}
