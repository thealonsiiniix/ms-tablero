package com.example.ms_tablero.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MoverRequest(
        @NotNull(message = "jugadorId es obligatorio") Long jugadorId,
        @NotNull(message = "posicionActual es obligatoria")
        @Min(value = 0, message = "posicionActual no puede ser negativa") Integer posicionActual,
        @NotNull(message = "dado es obligatorio")
        @Min(value = 1, message = "dado debe ser al menos 1")
        @Max(value = 6, message = "dado no puede ser mayor que 6") Integer dado) {
}
