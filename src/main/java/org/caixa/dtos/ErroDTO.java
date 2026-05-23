package org.caixa.dtos;

import java.time.LocalDateTime;

public record ErroDTO(
        LocalDateTime date,
        int status,
        String erro,
        String mensagem
) {
}
