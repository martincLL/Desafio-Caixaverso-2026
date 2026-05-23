package org.caixa.resources;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.caixa.dtos.SimulacaoResponseDTO;
import org.caixa.exceptions.SimulacaoNaoEncontradaException;
import org.caixa.services.SimulacaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class SimulacaoResourceTest {

    @InjectMock
    SimulacaoService simulacaoService;

    @Test
    @DisplayName("Deve retornar Status 200 (OK) ao buscar todas as simulações")
    void deveRetornar200AoBuscarTodasSimulacoes() {
        Mockito.when(simulacaoService.buscarTodasSimulacoes()).thenReturn(Collections.emptyList());

        given().when().get("/simulacoes").then().statusCode(200);
    }

    @Test
    @DisplayName("Deve retornar Status 200 (OK) ao buscar uma simulação cadastrada por id")
    void deveRetornar200AoBuscarPorIdExistente() {
        SimulacaoResponseDTO responseMock = new SimulacaoResponseDTO(
                15L,
                new BigDecimal("1104.08"),
                new BigDecimal("104.08"),
                new ArrayList<>()
        );
        Mockito.when(simulacaoService.buscarSimulacaoPorId(15L)).thenReturn(responseMock);

        given().when().get("/simulacoes/15").then().statusCode(200).body("id", is(15));
    }

    @Test
    @DisplayName("Deve retornar Status 404 (NOT FOUND) ao buscar um id que não existe")
    void deveRetornar404AoBuscarIdInexistente() {
        Mockito.when(simulacaoService.buscarSimulacaoPorId(77L))
                .thenThrow(new SimulacaoNaoEncontradaException("Simulação não encontrada"));

        given().when().get("/simulacoes/77").then().statusCode(404).body("status", is(404));
    }

    @Test
    @DisplayName("Deve Retornar Status 201 (CREATED) ao criar uma simulação válida")
    void deveRetornar201AoCriarSimulacaoValida() {
        String jsonValido = """
                {
                "valorInicial": 1000.00,
                "taxaJurosMensal": 2.0,
                "prazoMeses": 5
                }
                """;
        SimulacaoResponseDTO responseMock = new SimulacaoResponseDTO(
                1L, new BigDecimal("1104.08"), new BigDecimal("104.08"), new ArrayList<>()
        );
        Mockito.when(simulacaoService.simular(any())).thenReturn(responseMock);
        given().contentType(ContentType.JSON).body(jsonValido).when().post("/simulacoes").then().statusCode(201).body("id", is(1));
    }

    @Test
    @DisplayName("Deve retornar Status 400 (BAD REQUEST) ao tentar criar uma simulação com dados ínvalidos")
    void deveRetornar400AoEnviarDadosInvalidos() {
        String jsonInvalido = """
                {
                "valorInicial": 1000.00,
                "taxaJurosMensal": 2.0,
                "prazoMeses": 0
                }
                """;

        given().contentType(ContentType.JSON).body(jsonInvalido).when().post("/simulacoes").then().statusCode(400).body("status", is(400));
    }
}
