package org.caixa.dtos;

import java.math.BigDecimal;
import java.util.List;

public record SimulacaoResponseDTO(
        Long id,
        BigDecimal valorTotalFinal,
        BigDecimal valorTotalJuros,
        List<MemoriaCalculoDTO> memoriaCalculo
) {
}
