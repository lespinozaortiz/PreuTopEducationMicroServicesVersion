package com.example.backendexamenService.controller;

import com.example.backendexamenService.models.Estudiante;
import com.example.backendexamenService.service.ExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/examen")
public class ExamenController {

    @Autowired
    private ExamenService examenService;

    @PostMapping("/cargar_notas")
    public ResponseEntity<String> cargarNotasDesdeCSV(@RequestParam("archivo") MultipartFile archivo) {
        try {
            examenService.cargarNotasDesdeCSV(archivo);
            examenService.calcularYActualizarPromedioParaTodosLosEstudiantes();
            return ResponseEntity.ok("Notas cargadas exitosamente");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al cargar notas desde el archivo CSV: " + e.getMessage());
        }

    }


    @GetMapping("/generar_reporte/{rutEstudiante}")
    public ResponseEntity<Estudiante> generarReporteEstudiante(@PathVariable Long rutEstudiante) {
        try {
            Estudiante reporte = examenService.generarReporteEstudiante(rutEstudiante);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Puedes personalizar este mensaje de error según tus necesidades.
        }
    }
}