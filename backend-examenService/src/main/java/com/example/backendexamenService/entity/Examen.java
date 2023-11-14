package com.example.backendexamenService.entity;

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
public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long idexamen;


    private LocalDate fecha;
    private int puntaje;
    private Long Rutestudiante;
}
