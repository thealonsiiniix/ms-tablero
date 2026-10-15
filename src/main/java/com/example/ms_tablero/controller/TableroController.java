package com.example.ms_tablero.controller;

import com.example.ms_tablero.config.TableroProperties;
import com.example.ms_tablero.dto.ConfiguracionResponse;
import com.example.ms_tablero.dto.MoverRequest;
import com.example.ms_tablero.model.Movimiento;
import com.example.ms_tablero.service.TableroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/v1/tablero")
public class TableroController {

    private final TableroService tableroService;
    private final TableroProperties reglas;

    public TableroController(TableroService tableroService, TableroProperties reglas) {
        this.tableroService = tableroService;
        this.reglas = reglas;
    }

    /** Calcula la casilla final de un jugador según el dado (RF-03). */
    @PostMapping("/mover")
    public ResponseEntity<Movimiento> moverJugador(@Valid @RequestBody MoverRequest request) {

        Movimiento movimiento = tableroService.moverJugador(
                request.jugadorId(),
                request.posicionActual(),
                request.dado()
        );

        return ResponseEntity.ok(movimiento);
    }

    /** Casilla meta y casillas trampa vigentes. */
    @GetMapping("/configuracion")
    public ResponseEntity<ConfiguracionResponse> configuracion() {
        return ResponseEntity.ok(new ConfiguracionResponse(reglas.meta(), new TreeMap<>(reglas.trampas())));
    }

    @GetMapping
    public ResponseEntity<List<Movimiento>> listarMovimientos() {
        return ResponseEntity.ok(tableroService.listarMovimientos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> buscarPorId(@PathVariable Long id) {

        Movimiento movimiento = tableroService.buscarPorId(id);

        if (movimiento == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(movimiento);
    }

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<Movimiento>> listarPorJugador(@PathVariable Long jugadorId) {
        return ResponseEntity.ok(tableroService.listarMovimientosJugador(jugadorId));
    }
}
