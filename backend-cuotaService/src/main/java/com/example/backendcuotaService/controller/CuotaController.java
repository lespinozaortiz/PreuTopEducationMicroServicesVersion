package com.example.backendcuotaService.controller;

import com.example.backendcuotaService.entity.Cuota;
import com.example.backendcuotaService.models.Estudiante;
import com.example.backendcuotaService.service.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuota")
public class CuotaController {

    @Autowired
    private CuotaService cuotaService;

    @GetMapping("/verificarEstudiante/{rut}")
    public ResponseEntity<String> verificarEstudiante(@PathVariable Long rut) {
        try {
            Estudiante estudiante = cuotaService.getEstudianteByRut(rut);

            if (estudiante != null) {
                // El servicio de cuota pudo obtener el estudiante
                return ResponseEntity.ok("El estudiante con Rut " + rut + " está disponible en el servicio de cuota.");
            } else {
                // El estudiante no fue encontrado
                return ResponseEntity.ok("No se encontró un estudiante con Rut " + rut + " en el servicio de cuota.");
            }
        } catch (Exception e) {
            // Manejar cualquier error que pueda ocurrir
            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }

    @PostMapping("/generar_cuotas")
    public ResponseEntity<String> generarCuotasParaEstudiante(@RequestParam Long rut) {
        try {
            cuotaService.generarCuotasParaEstudiante(rut);
            return ResponseEntity.ok("Cuotas generadas exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: " + e.getMessage());
        }
    }
    @GetMapping("/ver_cuotas/{rut}")
    public List<Cuota> obtenerCuotasPorRutEstudiante(@PathVariable Long rut) {
        return cuotaService.obtenerCuotasPorRutEstudiante(rut);
    }

    @PostMapping("/pagar_cuota/{rutEstudiante}/{idCuota}")
    public ResponseEntity<String> pagarCuota(@PathVariable Long idCuota, @PathVariable Long rutEstudiante) {
        // Verificar si la cuota existe
        Cuota cuota = cuotaService.obtenerCuotaPorId(idCuota);

        if (cuota != null) {
            // Verificar que la cuota pertenezca al estudiante proporcionado
            if (cuota.getRutestudiante() != null && cuota.getRutestudiante().equals(rutEstudiante)) {
                cuotaService.actualizarEstadoCuota(idCuota, "Pagado");
                return new ResponseEntity<>("Cuota marcada como Pagado", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("La cuota no pertenece al estudiante proporcionado", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Cuota no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/aplicar_descuento")
    public ResponseEntity<String> aplicarDescuento() {
        try {
            cuotaService.aplicarDescuentoPorPromedioExamen();
            return ResponseEntity.ok("Descuento aplicado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: " + e.getMessage());
        }
    }



}

