package org.caixa.resources;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.caixa.dtos.ErroDTO;
import org.caixa.dtos.SimulacaoRequestDTO;
import org.caixa.dtos.SimulacaoResponseDTO;
import org.caixa.services.SimulacaoService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;

@Path("/simulacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SimulacaoResource {

    @Inject
    SimulacaoService simulacaoService;

    @GET
    @Operation(summary = "Listar Todas as Simulações", description = "Retorna uma lista com todas as simulações salvas no banco de dados.")
    @APIResponse(responseCode = "200", description = "Lista recuperada com sucesso",
            content = @Content(mediaType = "application/json"))
    public Response buscarTodasSimulacoes() {
        List<SimulacaoResponseDTO> listaSimulacoes = simulacaoService.buscarTodasSimulacoes();
        return Response.ok(listaSimulacoes).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar Simulação por ID", description = "Retorna a simulação e sua memória de cálculo.")
    @APIResponse(responseCode = "200", description = "Simulação encontrada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SimulacaoResponseDTO.class)))
    @APIResponse(responseCode = "404", description = "Simulação não encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroDTO.class)))
    public Response buscarSimulacaoPorId(@PathParam("id") Long id) {
        SimulacaoResponseDTO simulacao = simulacaoService.buscarSimulacaoPorId(id);
        return Response.ok(simulacao).build();
    }

    @POST
    @Operation(summary = "Criar Nova Simulação", description = "Realiza os cálculos da simulação e salva no banco de dados.")
    @APIResponse(responseCode = "201", description = "Simulação criada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SimulacaoResponseDTO.class)))
    @APIResponse(responseCode = "400", description = "Erro de validação nos dados enviados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroDTO.class)))
    public Response criarSimulacao(@Valid SimulacaoRequestDTO requestDTO) {
        SimulacaoResponseDTO responseDTO = simulacaoService.simular(requestDTO);
        return Response.status(Response.Status.CREATED).entity(responseDTO).build();
    }
}
