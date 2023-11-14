package com.example.backendexamenService.repository;

import com.example.backendexamenService.entity.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenRepository extends JpaRepository<Examen,Long> {


    List<Examen> findExamenByRutestudiante(Long rutEstudiante);
}
