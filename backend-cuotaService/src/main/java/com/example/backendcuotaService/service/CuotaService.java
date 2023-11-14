package com.example.backendcuotaService.service;
import com.example.backendcuotaService.entity.Cuota;
import com.example.backendcuotaService.models.Estudiante;
import com.example.backendcuotaService.repository.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class CuotaService {


    private final String ESTUDIANTE_SERVICE_URL = "http://backend-estudianteService/estudiante"; // Reemplaza con la URL correcta de tu servicio de estudiantes.

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CuotaRepository cuotaRepository;

    public Estudiante getEstudianteByRut(Long rut) {
        String url = ESTUDIANTE_SERVICE_URL + "/estudiante/{rut}";
        return restTemplate.getForObject(url, Estudiante.class, rut);
    }


    public void generarCuotasParaEstudiante(Long rut) {
        // Obtener el estudiante por su rut
        Estudiante estudiante = getEstudianteByRut(rut);

        if (estudiante != null) {
            // Lógica para generar cuotas
            double arancelTotal = 1500000.0; // Se utiliza arancel fijo de 1.500.000

            // Calculo descuento según tipo de colegio de procedencia
            double descuentoTipo = 0;
            if ("Municipal".equals(estudiante.getTipo_colegio_proc())) {
                descuentoTipo = 0.2 * arancelTotal; // 20% de descuento
            } else if ("Subvencionado".equals(estudiante.getTipo_colegio_proc())) {
                descuentoTipo = 0.1 * arancelTotal; // 10% de descuento
            }

            // Calculo descuento según años desde que egresó del colegio
            int anosDesdeEgreso = Year.now().getValue() - estudiante.getEgreso();
            double descuentoAnios = 0;
            if (anosDesdeEgreso < 1) {
                descuentoAnios = 0.15 * arancelTotal; // 15% de descuento
            } else if (anosDesdeEgreso >= 1 && anosDesdeEgreso <= 2) {
                descuentoAnios = 0.08 * arancelTotal; // 8% de descuento
            } else if (anosDesdeEgreso >= 3 && anosDesdeEgreso <= 4) {
                descuentoAnios = 0.04 * arancelTotal; // 4% de descuento
            }

            // Se aplica ambos descuentos al arancel total
            arancelTotal -= (descuentoTipo + descuentoAnios);

            // Calculo número de cuotas que debe pagar el estudiante
            int cantidadCuotas = estudiante.getCantidad_cuotas();
            int montoCuota = (int) Math.ceil(arancelTotal / cantidadCuotas); // Redondeo hacia arriba

            // Obtener el mes y año actual
            YearMonth yearMonth = YearMonth.now();
            LocalDate fechaActual = LocalDate.now();

            // Verificación si la fecha actual es después del plazo de pago del mes actual
            if (fechaActual.getDayOfMonth() > 10) {
                // Avanza al siguiente mes si la fecha actual es después del plazo de pago
                yearMonth = yearMonth.plusMonths(1);
            }

            // Generar cuotas y guardar en la base de datos
            for (int i = 0; i < cantidadCuotas; i++) {
                LocalDate plazoInicio = yearMonth.atDay(5);
                LocalDate plazoFinal = yearMonth.atDay(10);

                Cuota cuota = new Cuota();
                cuota.setRutestudiante(estudiante.getRut());
                cuota.setMonto(montoCuota);
                cuota.setEstado("Pendiente");
                cuota.setPlazo_inicio(plazoInicio);
                cuota.setPlazo_final(plazoFinal);
                cuota.setInteres(0); // No hay intereses en este ejemplo
                cuota.setDescuento(0); // No se aplica descuento directamente a las cuotas
                cuotaRepository.save(cuota);

                // Avanzar al siguiente mes para las siguientes cuotas
                yearMonth = yearMonth.plusMonths(1);
            }
        }
    }


    public List<Cuota> obtenerCuotasPorRutEstudiante(Long rut) {
        return cuotaRepository.findCuotasByRutestudiante(rut);
    }

    public Cuota obtenerCuotaPorId(Long idCuota) {
        // Lógica para obtener cuota por ID desde el repositorio
        return cuotaRepository.findById(idCuota).orElse(null);
    }

    public Cuota actualizarEstadoCuota(Long idCuota, String nuevoEstado) {
        // Lógica para actualizar el estado de la cuota
        Cuota cuota = cuotaRepository.findById(idCuota).orElse(null);
        if (cuota != null) {
            cuota.setEstado(nuevoEstado);
            cuotaRepository.save(cuota);
        }
        return cuota;
    }

    public List<Estudiante> getAllEstudiantes() {
        String url = ESTUDIANTE_SERVICE_URL + "/estudiantes";

        // Hacer una solicitud al servicio de estudiantes usando RestTemplate
        ResponseEntity<List<Estudiante>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Estudiante>>() {
                });

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("Error al obtener la lista de estudiantes");
        }
    }

    public void aplicarDescuentoPorPromedioExamen() {
        // Obtener todos los estudiantes usando RestTemplate
        List<Estudiante> estudiantes = getAllEstudiantes();

        for (Estudiante estudiante : estudiantes) {
            double descuentoPorcentaje = 0.0;

            if (estudiante.getPromedio_examen() >= 950) {
                descuentoPorcentaje = 0.10; // 10% de descuento
            } else if (estudiante.getPromedio_examen() >= 900) {
                descuentoPorcentaje = 0.05; // 5% de descuento
            } else if (estudiante.getPromedio_examen() >= 850) {
                descuentoPorcentaje = 0.02; // 2% de descuento
            }

            // Obtener las cuotas pendientes del estudiante
            List<Cuota> cuotas = cuotaRepository.findByRutestudianteAndEstado(estudiante.getRut(), "Pendiente");


            // Aplicar descuento a las cuotas y actualizar en la base de datos
            for (Cuota cuota : cuotas) {
                int descuentoActual = cuota.getDescuento(); // Obtener el descuento acumulado
                int montoOriginal = cuota.getMonto();
                double nuevoDescuento = montoOriginal * descuentoPorcentaje; // Calcular nuevo descuento

                // Sumar el nuevo descuento al descuento acumulado
                descuentoActual += nuevoDescuento;

                // Aplicar el descuento acumulado al monto de la cuota
                int montoConDescuento = (int) Math.ceil(montoOriginal - descuentoActual);

                // Actualizar el atributo descuento de la cuota
                cuota.setDescuento(descuentoActual);

                // Actualizar el monto de la cuota con el descuento acumulado
                cuota.setMonto(montoConDescuento);

                // Guardar la cuota actualizada en la base de datos
                cuotaRepository.save(cuota);
            }
        }
    }
}

