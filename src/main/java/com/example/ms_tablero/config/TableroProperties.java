package com.example.ms_tablero.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Map;

/**
 * Reglas configurables del tablero (RF-03).
 *
 * @param meta    número de la casilla final; quien llega o la pasa gana
 * @param trampas casillas trampa: casilla de origen -> casilla a la que se retrocede
 */
@ConfigurationProperties(prefix = "tablero")
public record TableroProperties(@DefaultValue("30") int meta, Map<Integer, Integer> trampas) {

    public TableroProperties {
        if (trampas == null) {
            trampas = Map.of();
        }
    }
}
