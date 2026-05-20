package org.caixa.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record SimulacaoRequestDTO(
        @NotNull(message = "O valor inicial não pode ser nulo")
        @Positive(message = "O valor inicial deve ser maior que zero")
        BigDecimal valorInicial,
        @NotNull(message = "A taxa de juros não pode ser nula")
        @Positive(message = "A taxa de juros deve ser maior que zero")
        BigDecimal taxaJurosMensal,
        @NotNull(message = "O prazo não pode ser nulo")
        @Min(value = 1, message = "O prazo mínimo para simulação é de 1 mês")
        Integer prazoMeses) {
}
