package com.example.ms_tablero.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jugadorId;

    private Integer posicionAnterior;

    private Integer dado;

    private Integer posicionNueva;

    /** true si la casilla de destino era una trampa y el jugador retrocedió. */
    private Boolean trampa;

    /** true si el jugador llegó (o pasó) la casilla meta con este movimiento. */
    private Boolean llegoAMeta;

    private LocalDateTime fecha;
}
