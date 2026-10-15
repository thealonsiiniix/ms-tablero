package com.example.ms_tablero.service;

import com.example.ms_tablero.config.TableroProperties;
import com.example.ms_tablero.model.Movimiento;
import com.example.ms_tablero.repository.MovimientoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TableroService {

    private final MovimientoRepository movimientoRepository;
    private final TableroProperties reglas;

    public TableroService(MovimientoRepository movimientoRepository, TableroProperties reglas) {
        this.movimientoRepository = movimientoRepository;
        this.reglas = reglas;
    }

    /**
     * Aplica las reglas del tablero (RF-03):
     * 1) avanza tantas casillas como indique el dado;
     * 2) si llega o pasa la meta, queda en la meta y gana;
     * 3) si cae en una casilla trampa, retrocede a la casilla indicada.
     */
    public Movimiento moverJugador(Long jugadorId, Integer posicionActual, Integer dado) {

        int meta = reglas.meta();

        if (posicionActual >= meta) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El jugador ya está en la meta (casilla " + meta + ")");
        }

        int posicionNueva = posicionActual + dado;
        boolean llegoAMeta = false;
        boolean trampa = false;

        if (posicionNueva >= meta) {
            posicionNueva = meta;
            llegoAMeta = true;
        } else if (reglas.trampas().containsKey(posicionNueva)) {
            posicionNueva = reglas.trampas().get(posicionNueva);
            trampa = true;
        }

        Movimiento movimiento = new Movimiento();
        movimiento.setJugadorId(jugadorId);
        movimiento.setPosicionAnterior(posicionActual);
        movimiento.setDado(dado);
        movimiento.setPosicionNueva(posicionNueva);
        movimiento.setTrampa(trampa);
        movimiento.setLlegoAMeta(llegoAMeta);
        movimiento.setFecha(LocalDateTime.now());

        return movimientoRepository.save(movimiento);
    }

    public List<Movimiento> listarMovimientos() {
        return movimientoRepository.findAllByOrderByIdAsc();
    }

    public List<Movimiento> listarMovimientosJugador(Long jugadorId) {
        return movimientoRepository.findByJugadorIdOrderByIdAsc(jugadorId);
    }

    public Movimiento buscarPorId(Long id) {
        return movimientoRepository.findById(id).orElse(null);
    }
}
