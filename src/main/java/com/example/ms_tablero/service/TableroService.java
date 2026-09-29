package com.example.ms_tablero.service;

import com.example.ms_tablero.model.Movimiento;
import com.example.ms_tablero.repository.MovimientoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TableroService {

    private final MovimientoRepository movimientoRepository;

    public TableroService(MovimientoRepository movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }

    public Movimiento moverJugador(Long jugadorId, Integer posicionActual, Integer dado) {

        Integer posicionNueva = posicionActual + dado;

        Boolean trampa = false;

        // Trampa de ejemplo
        if (posicionNueva == 10) {
            posicionNueva = 5;
            trampa = true;
        }

        Movimiento movimiento = new Movimiento();

        movimiento.setJugadorId(jugadorId);
        movimiento.setPosicionAnterior(posicionActual);
        movimiento.setDado(dado);
        movimiento.setPosicionNueva(posicionNueva);
        movimiento.setTrampa(trampa);
        movimiento.setFecha(LocalDateTime.now());

        return movimientoRepository.save(movimiento);
    }

    public List<Movimiento> listarMovimientos() {
        return movimientoRepository.findAll();
    }

    public List<Movimiento> listarMovimientosJugador(Long jugadorId) {
        return movimientoRepository.findByJugadorId(jugadorId);
    }

    public Movimiento buscarPorId(Long id) {
        return movimientoRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        movimientoRepository.deleteById(id);
    }
}