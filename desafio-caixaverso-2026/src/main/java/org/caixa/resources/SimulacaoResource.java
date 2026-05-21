package org.caixa.resources;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.caixa.dtos.SimulacaoRequestDTO;
import org.caixa.dtos.SimulacaoResponseDTO;
import org.caixa.services.SimulacaoService;

import java.util.List;

@Path("/simulacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SimulacaoResource {

    @Inject
    SimulacaoService simulacaoService;

    @GET
    public Response buscarTodasSimulacoes() {
        List<SimulacaoResponseDTO> listaSimulacoes = simulacaoService.buscarTodasSimulacoes();
        return Response.ok(listaSimulacoes).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarSimulacaoPorId(@PathParam("id") Long id) {
        SimulacaoResponseDTO simulacao = simulacaoService.buscarSimulacaoPorId(id);
        return Response.ok(simulacao).build();
    }

    @POST
    public Response criarSimulacao(@Valid SimulacaoRequestDTO requestDTO) {
        SimulacaoResponseDTO responseDTO = simulacaoService.simular(requestDTO);
        return Response.status(Response.Status.CREATED).entity(responseDTO).build();
    }
}
