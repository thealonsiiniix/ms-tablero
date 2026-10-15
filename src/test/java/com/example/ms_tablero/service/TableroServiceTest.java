package com.example.ms_tablero.service;

import com.example.ms_tablero.config.TableroProperties;
import com.example.ms_tablero.model.Movimiento;
import com.example.ms_tablero.repository.MovimientoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TableroServiceTest {

    private final MovimientoRepository repo = mock(MovimientoRepository.class);
    private final TableroProperties reglas = new TableroProperties(30, Map.of(10, 5, 17, 7, 23, 12));
    private final TableroService service = new TableroService(repo, reglas);

    private Movimiento mover(int posicion, int dado) {
        when(repo.save(any(Movimiento.class))).thenAnswer(inv -> inv.getArgument(0));
        return service.moverJugador(1L, posicion, dado);
    }

    @Test
    void avanzaLasCasillasDelDado() {
        Movimiento m = mover(3, 4);

        assertEquals(7, m.getPosicionNueva());
        assertFalse(m.getTrampa());
        assertFalse(m.getLlegoAMeta());
    }

    @Test
    void casillaTrampaHaceRetroceder() {
        Movimiento m = mover(6, 4); // 6 + 4 = 10, trampa 10 -> 5

        assertEquals(5, m.getPosicionNueva());
        assertTrue(m.getTrampa());
        assertFalse(m.getLlegoAMeta());
    }

    @Test
    void otraTrampaConfiguradaTambienFunciona() {
        Movimiento m = mover(20, 3); // 23 -> 12

        assertEquals(12, m.getPosicionNueva());
        assertTrue(m.getTrampa());
    }

    @Test
    void llegarExactoALaMetaGana() {
        Movimiento m = mover(27, 3);

        assertEquals(30, m.getPosicionNueva());
        assertTrue(m.getLlegoAMeta());
        assertFalse(m.getTrampa());
    }

    @Test
    void pasarseDeLaMetaQuedaEnLaMetaYGana() {
        Movimiento m = mover(28, 6);

        assertEquals(30, m.getPosicionNueva());
        assertTrue(m.getLlegoAMeta());
    }

    @Test
    void unJugadorQueYaEstaEnLaMetaNoPuedeMoverse() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> service.moverJugador(1L, 30, 2));

        assertEquals(400, ex.getStatusCode().value());
    }
}
