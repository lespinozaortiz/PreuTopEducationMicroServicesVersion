package com.example.backendestudianteService.repository;

import com.example.backendestudianteService.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante,Long> {
    Optional<Estudiante> findEstudianteByRut(Long rut);//el valor puede ser nulo


    List<Estudiante> findEstudiantesByTipopago(String tipopago);


}
