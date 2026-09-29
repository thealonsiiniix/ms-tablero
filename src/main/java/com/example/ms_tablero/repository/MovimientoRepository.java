package com.example.ms_tablero.repository;

import com.example.ms_tablero.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByJugadorId(Long jugadorId);
}