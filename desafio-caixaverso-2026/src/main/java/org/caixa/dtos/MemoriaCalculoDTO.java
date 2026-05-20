package org.caixa.dtos;

import java.math.BigDecimal;

public record MemoriaCalculoDTO(
        Integer mes,
        BigDecimal saldoInicial,
        BigDecimal juroAplicado,
        BigDecimal saldoFinal
) {
}
