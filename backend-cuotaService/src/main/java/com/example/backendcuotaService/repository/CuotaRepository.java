package com.example.backendcuotaService.repository;

import com.example.backendcuotaService.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long> {
    List<Cuota> findByEstudiante_Rut(Long estudianteId);
    List<Cuota> findByEstudiante_RutAndEstado(Long estudianteId, String estado);
    Cuota findCuotaByIdcuota(Long idcuota);
}
