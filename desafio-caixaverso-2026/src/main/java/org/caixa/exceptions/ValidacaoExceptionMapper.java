package org.caixa.exceptions;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.caixa.dtos.ErroDTO;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Provider
public class ValidacaoExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        String mensagem = exception.getConstraintViolations()
                .stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.joining(", "));

        ErroDTO erroDTO = new ErroDTO(
                LocalDateTime.now(),
                400,
                "Erro de validação",
                mensagem
        );
        return Response.status(Response.Status.BAD_REQUEST).entity(erroDTO).build();
    }
}
