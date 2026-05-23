package org.caixa.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.caixa.dtos.ErroDTO;

import java.time.LocalDateTime;

@Provider
public class SimulacaoNaoEncontradaExceptionMapper implements ExceptionMapper<SimulacaoNaoEncontradaException> {

    @Override
    public Response toResponse(SimulacaoNaoEncontradaException exception) {
        ErroDTO erro = new ErroDTO(
                LocalDateTime.now(),
                Response.Status.NOT_FOUND.getStatusCode(),
                Response.Status.NOT_FOUND.getReasonPhrase(),
                exception.getMessage()
        );

        return Response.status(Response.Status.NOT_FOUND)
                .entity(erro)
                .build();
    }
}
