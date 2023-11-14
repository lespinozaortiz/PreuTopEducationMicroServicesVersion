package com.example.backendcuotaService.repository;

import com.example.backendcuotaService.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long> {

    Cuota findCuotaByIdcuota(Long idcuota);
    List<Cuota> findByRutestudianteAndEstado(Long rutestudiante, String estado);

    List<Cuota> findCuotasByRutestudiante(Long rut);
}
