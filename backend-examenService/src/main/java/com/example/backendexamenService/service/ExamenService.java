package com.example.backendexamenService.service;

import com.example.backendexamenService.entity.Examen;
import com.example.backendexamenService.models.Cuota;
import com.example.backendexamenService.models.Estudiante;
import com.example.backendexamenService.repository.ExamenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamenService {
    private final String ESTUDIANTE_SERVICE_URL = "http://backend-estudianteService/estudiante"; // Reemplaza con la URL correcta de tu servicio de estudiantes.
    private final String CUOTA_SERVICE_URL = "http://backend-cuotaService/cuota";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ExamenRepository examenRepository;

    public Estudiante getEstudianteByRut(Long rut) {
        String url = ESTUDIANTE_SERVICE_URL + "/estudiante/{rut}";
        return restTemplate.getForObject(url, Estudiante.class, rut);
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

    public Cuota getCuotaByRut(Long rut) {
        String url = CUOTA_SERVICE_URL + "/ver_cuotas/{rut}";
        return restTemplate.getForObject(url, Cuota.class, rut);
    }

    public void cargarNotasDesdeCSV(MultipartFile archivo) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(archivo.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                Long rutEstudiante = Long.parseLong(csvRecord.get("rutEstudiante"));
                String fechaExamen = csvRecord.get("fechaExamen");
                LocalDate fecha = LocalDate.parse(fechaExamen);
                int puntaje = Integer.parseInt(csvRecord.get("puntajeObtenido"));

                // Buscar estudiante por rut
                Estudiante estudiante = getEstudianteByRut(rutEstudiante);

                // Crea un objeto Examen y se guarda en la base de datos
                Examen examen = new Examen();
                examen.setRutestudiante(rutEstudiante);
                examen.setFecha(fecha);
                examen.setPuntaje(puntaje);
                examenRepository.save(examen);
            }
        }
    }

    public List<Examen> obtenerExamenesPorEstudiante(Long rutEstudiante) {
        return examenRepository.findExamenByRutestudiante(rutEstudiante);
    }

    public void calcularYActualizarPromedioParaTodosLosEstudiantes() {
        List<Estudiante> estudiantes = getAllEstudiantes();
        for (Estudiante estudiante : estudiantes) {
            List<Examen> examenes = obtenerExamenesPorEstudiante(estudiante.getRut());
            int sumPuntajes = examenes.stream().mapToInt(Examen::getPuntaje).sum();
            int totalExamenes = examenes.size();
            int promedio = (totalExamenes == 0) ? 0 : sumPuntajes / totalExamenes;
            estudiante.setPromedio_examen(promedio);
        }
    }

    private List<Cuota> obtenerCuotasPagadas(Long rutEstudiante) {
        String url = CUOTA_SERVICE_URL + "/ver_cuotas/{rut}";
        ResponseEntity<List<Cuota>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Cuota>>() {
                },
                rutEstudiante);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // Filtra las cuotas por estado "Pagado"
            return responseEntity.getBody().stream()
                    .filter(cuota -> "Pagado".equals(cuota.getEstado()))
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Error al obtener las cuotas pagadas");
        }
    }
    private List<Cuota> obtenerCuotasPendientes(Long rutEstudiante) {
        String url = CUOTA_SERVICE_URL + "/ver_cuotas/{rut}";
        ResponseEntity<List<Cuota>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Cuota>>() {
                },
                rutEstudiante);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            // Filtra las cuotas por estado "Pendiente"
            return responseEntity.getBody().stream()
                    .filter(cuota -> "Pendiente".equals(cuota.getEstado()))
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Error al obtener las cuotas pendiente");
        }
    }
    public Estudiante generarReporteEstudiante(Long rutEstudiante) {
        Estudiante estudiante = getEstudianteByRut(rutEstudiante);

        // Calcula cantidad de exámenes rendidos
        List<Examen> examenes = obtenerExamenesPorEstudiante(rutEstudiante);
        int cantidadExamenesRendidos = examenes.size();
        estudiante.setCantidad_examenes(cantidadExamenesRendidos);

        // Calcula monto total del arancel a pagar
        List<Cuota> cuotasPagadas = obtenerCuotasPagadas(rutEstudiante);
        int montoTotalPagado = cuotasPagadas.stream().mapToInt(Cuota::getMonto).sum();
        estudiante.setMonto_pagado(montoTotalPagado);

        // Calcula saldo por pagar
        List<Cuota> cuotasPendientes =obtenerCuotasPendientes(rutEstudiante);
        int saldoPorPagar = cuotasPendientes.stream().mapToInt(Cuota::getMonto).sum();
        estudiante.setSaldo_por_pagar(saldoPorPagar);

        // Calcula número de cuotas con retraso
        long numeroCuotasRetraso = cuotasPendientes.stream().filter(cuota -> cuota.getEstado().equals("Retraso")).count();
        estudiante.setCuotas_retraso((int) numeroCuotasRetraso);

        // Calcula fecha del último pago
        LocalDate fechaUltimoPago = cuotasPagadas.stream().map(Cuota::getFecha_pago).max(LocalDate::compareTo).orElse(null);
        estudiante.setUltimo_pago(fechaUltimoPago);

        // Calcula número de cuotas pagadas
        int numeroCuotasPagadas = cuotasPagadas.size();
        estudiante.setNumero_cuotas_pagadas(numeroCuotasPagadas);

        // Calcula monto total del arancel a pagar (saldo por pagar + monto pagado)
        int arancelAPagar = estudiante.getSaldo_por_pagar() + estudiante.getMonto_pagado();
        estudiante.setArancel_a_pagar(arancelAPagar);

        return estudiante;
    }

    public List<Estudiante> obtenerTodosLosEstudiantes() {
        return getAllEstudiantes();
    }


}
