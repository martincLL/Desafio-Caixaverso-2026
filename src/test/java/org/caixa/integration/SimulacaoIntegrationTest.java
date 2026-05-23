package org.caixa.integration;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class SimulacaoIntegrationTest {

    @Test
    @DisplayName("Deve realizar o proceso completo de criação de simulação e consulta no BD")
    void deveRealizarProcessoCompletoSimulacaoIntegracao() {
        String jsonValido = """
                {
                  "valorInicial": 2000.00,
                  "taxaJurosMensal": 1.0,
                  "prazoMeses": 2
                }
                """;

        Integer idSimulacaoCriada =
                given()
                    .contentType(ContentType.JSON)
                    .body(jsonValido)
                .when()
                    .post("/simulacoes")
                .then()
                    .statusCode(201)
                    .body("valorTotalFinal", is(2040.20F))
                    .body("memoriaCalculo", hasSize(2))
                    .extract().path("id");

                given()
                .when()
                    .get("/simulacoes/" + idSimulacaoCriada)
                .then()
                    .statusCode(200)
                    .body("id", is(idSimulacaoCriada))
                    .body("valorTotalJuros", is(40.20F));
    }

    @Test
    @DisplayName("DDeve retornar erro 400 (BAD REQUEST) ao enviar dados ínvalidos")
    void deveRetornar400AoEnviarDadosInvalidosIntegracao() {
        String jsonInvalido = """
                {
                  "valorInicial": 1000.00,
                  "taxaJurosMensal": 2.0,
                  "prazoMeses": 0
                }
                """;

        given()
            .contentType(ContentType.JSON)
            .body(jsonInvalido)
        .when()
            .post("/simulacoes")
        .then()
            .statusCode(400)
            .body("status", is(400));
    }

    @Test
    @DisplayName("Deve retornar 404 (NOT FOUND) ao buscar id inexistente no BD")
    void deveRetornar404AoBuscarIdInexistenteIntegracao() {
        given()
        .when()
            .get("/simulacoes/777")
        .then()
            .statusCode(404)
            .body("status", is(404))
            .body("erro", is("Not Found"))
            .body("mensagem", is("Simulação não encontrada"));
    }
}
