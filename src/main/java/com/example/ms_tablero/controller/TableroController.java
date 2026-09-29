package com.example.ms_tablero.controller;

import com.example.ms_tablero.model.Movimiento;
import com.example.ms_tablero.service.TableroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tablero")
public class TableroController {

    private final TableroService tableroService;

    public TableroController(TableroService tableroService) {
        this.tableroService = tableroService;
    }

    @PostMapping("/mover")
    public ResponseEntity<Movimiento> moverJugador(
            @RequestParam Long jugadorId,
            @RequestParam Integer posicionActual,
            @RequestParam Integer dado) {

        Movimiento movimiento = tableroService.moverJugador(
                jugadorId,
                posicionActual,
                dado
        );

        return ResponseEntity.ok(movimiento);
    }

    @GetMapping
    public ResponseEntity<List<Movimiento>> listarMovimientos() {

        return ResponseEntity.ok(
                tableroService.listarMovimientos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> buscarPorId(
            @PathVariable Long id) {

        Movimiento movimiento = tableroService.buscarPorId(id);

        if (movimiento == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(movimiento);
    }

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<Movimiento>> listarPorJugador(
            @PathVariable Long jugadorId) {

        return ResponseEntity.ok(
                tableroService.listarMovimientosJugador(jugadorId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        tableroService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}