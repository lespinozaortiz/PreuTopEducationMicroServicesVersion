package com.example.backendestudianteService.service;

import com.example.backendestudianteService.entity.Estudiante;
import com.example.backendestudianteService.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {
    private final EstudianteRepository estudianteRepository;
    @Autowired
    public EstudianteService(EstudianteRepository estudianteRepository){
        this.estudianteRepository = estudianteRepository;
    }
    public List<Estudiante> getEstudiantes() {
        return this.estudianteRepository.findAll();
    }

    public Estudiante registrarEstudiante(Estudiante estudiante){

        return estudianteRepository.save(estudiante);
    }

    public Estudiante actualizarEstudiante(Estudiante estudiante){

        return estudianteRepository.save(estudiante);
    }
    public Estudiante obtenerEstudianteporRut(Long rut){
        return estudianteRepository.findById(rut).get();

    }

    public void eliminarEstudiante(Long rut){

        estudianteRepository.deleteById(rut);
    }
    public List<Estudiante> getEstudianteporTipopago(String tipopago) {
        return estudianteRepository.findEstudiantesByTipopago(tipopago);
    }
}
