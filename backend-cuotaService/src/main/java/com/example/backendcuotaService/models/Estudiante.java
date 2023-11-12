package com.example.backendcuotaService.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {
    @Id
    @Column(name = "rut", nullable = false, unique = true)
    private Long rut;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String tipoColegioProc;
    private String nombreColegio;
    private int egreso;
    private String tipoPago;
    private int arancelAPagar;
    private int cantidadCuotas;
    private int promedioExamen;
    private int cantidadExamenes;
    private int montoPagado;
    private int saldoPorPagar;
    private int cuotasRetraso;
    private LocalDate ultimoPago;
    private int numeroCuotasPagadas;
}
