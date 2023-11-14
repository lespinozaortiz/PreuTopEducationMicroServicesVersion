package com.example.backendexamenService.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long idcuota;
    private LocalDate fecha_pago;
    private int monto;
    private String estado;
    private LocalDate plazo_inicio;
    private LocalDate plazo_final;
    private int interes;
    private int descuento;

    private Long rutestudiante;
}
