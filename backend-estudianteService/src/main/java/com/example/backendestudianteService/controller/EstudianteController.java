package com.example.backendestudianteService.controller;

import com.example.backendestudianteService.entity.Estudiante;
import com.example.backendestudianteService.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void registrarEstudiante(@RequestParam("rutDigits") String rutDigits,
                                    @RequestParam("rutVerifier") String rutVerifier,
                                    @RequestBody Estudiante estudiante) {
        String rutCombined = rutDigits + rutVerifier;
        long rutLong = Long.parseLong(rutCombined);
        estudiante.setRut(rutLong);
        estudianteService.registrarEstudiante(estudiante);
    }

    @GetMapping("/editar_estudiante/{id}")
    public Estudiante editarEstudiante(@PathVariable Long id) {
        return estudianteService.obtenerEstudianteporRut(id);
    }

    @PostMapping("/editar_estudiante/{id}")
    public void editarEstudiante(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        Estudiante estudianteIngresado = estudianteService.obtenerEstudianteporRut(id);
        estudianteIngresado.setRut(id);
        estudianteIngresado.setNombres(estudiante.getNombres());
        estudianteIngresado.setRut(estudiante.getRut());
        estudianteIngresado.setApellidos(estudiante.getApellidos());
        estudianteIngresado.setFecha_nacimiento(estudiante.getFecha_nacimiento());
        estudianteIngresado.setTipo_colegio_proc(estudiante.getTipo_colegio_proc());
        estudianteIngresado.setNombre_colegio(estudiante.getNombre_colegio());
        estudianteIngresado.setEgreso(estudiante.getEgreso());
        estudianteService.actualizarEstudiante(estudianteIngresado);
    }

    @GetMapping("/eliminar_estudiante/{id}")
    public void eliminarEstudiante(@PathVariable Long id) {
        estudianteService.eliminarEstudiante(id);
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
