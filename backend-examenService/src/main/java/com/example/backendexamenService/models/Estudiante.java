package com.example.backendexamenService.models;

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
    private LocalDate fecha_nacimiento;
    private String tipo_colegio_proc;
    private String nombre_colegio;
    private int egreso;
    private String tipopago;
    private int arancel_a_pagar;
    private int cantidad_cuotas;
    private int promedio_examen;
    private int cantidad_examenes;
    private int monto_pagado;
    private int saldo_por_pagar;
    private int cuotas_retraso;
    private LocalDate ultimo_pago;
    private int numero_cuotas_pagadas;
}
