package com.example.backendestudianteService.controller;

import com.example.backendestudianteService.entity.Estudiante;
import com.example.backendestudianteService.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/estudiante")
public class EstudianteController {

    private final EstudianteService estudianteService;

    @Autowired
    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping("/estudiantes")
    public List<Estudiante> getEstudiantes() {
        return estudianteService.getEstudiantes();
    }

    @GetMapping("/ingreso_estudiante")
    public Estudiante registroEstudiante() {
        return new Estudiante();
    }

    @PostMapping("/ingreso_estudiante")
    public ResponseEntity<String> registrarEstudiante(@RequestBody Estudiante estudiante) {
        try {
            // Lógica para registrar el estudiante
            estudianteService.registrarEstudiante(estudiante);

            return ResponseEntity.ok("Estudiante registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: " + e.getMessage());
        }
    }



    @GetMapping("/estudiante/{rut}")
    public Estudiante getEstudianteByRut(@PathVariable Long rut) {
        return estudianteService.obtenerEstudianteporRut(rut);
    }

    @PostMapping("/editar_estudiante/{id}")
    public ResponseEntity<String> editarEstudiante(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        try {
            // Obtener el estudiante existente por ID
            Estudiante estudianteIngresado = estudianteService.obtenerEstudianteporRut(id);

            // Verificar si el estudiante existe
            if (estudianteIngresado != null) {
                // Actualizar los campos necesarios
                estudianteIngresado.setRut(id);
                estudianteIngresado.setNombres(estudiante.getNombres());
                estudianteIngresado.setApellidos(estudiante.getApellidos());
                estudianteIngresado.setFecha_nacimiento(estudiante.getFecha_nacimiento());
                estudianteIngresado.setTipo_colegio_proc(estudiante.getTipo_colegio_proc());
                estudianteIngresado.setNombre_colegio(estudiante.getNombre_colegio());
                estudianteIngresado.setEgreso(estudiante.getEgreso());

                // Actualizar el estudiante en el servicio
                estudianteService.actualizarEstudiante(estudianteIngresado);

                // Devolver una respuesta exitosa
                return ResponseEntity.ok("Estudiante actualizado exitosamente");
            } else {
                // Devolver respuesta indicando que el estudiante no fue encontrado
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante no encontrado");
            }
        } catch (Exception e) {
            // Devolver respuesta con error interno del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: " + e.getMessage());
        }
    }


    @GetMapping("/eliminar_estudiante/{id}")
    public ResponseEntity<String> eliminarEstudiante(@PathVariable Long id) {
        try {
            estudianteService.eliminarEstudiante(id);
            return ResponseEntity.ok("Estudiante eliminado exitosamente");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: " + e.getMessage());
        }
    }


    @GetMapping("/generar_cuota")
    public List<Estudiante> getEstudiantesCuota() {
        return estudianteService.getEstudianteporTipopago("Cuotas");
    }

    @GetMapping("/actualizar_cuota/{id}")
    public Estudiante cantidadcuotasdeEstudiante(@PathVariable Long id) {
        return estudianteService.obtenerEstudianteporRut(id);
    }

    @PostMapping("/actualizar_cuota/{id}")
    public void ingresarcuotaEstudiante(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        Estudiante estudianteIngresado = estudianteService.obtenerEstudianteporRut(id);
        estudianteIngresado.setCantidad_cuotas(estudiante.getCantidad_cuotas());
        estudianteService.actualizarEstudiante(estudianteIngresado);
    }

    @GetMapping("/cuotas")
    public List<Estudiante> getEstudiantesconCuota() {
        return estudianteService.getEstudianteporTipopago("Cuotas");
    }
}
