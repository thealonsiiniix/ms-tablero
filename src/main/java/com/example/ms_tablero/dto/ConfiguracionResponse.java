package com.example.ms_tablero.dto;

import java.util.Map;

public record ConfiguracionResponse(int meta, Map<Integer, Integer> trampas) {
}
