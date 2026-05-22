package org.caixa.entities;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class MemoriaCalculoTest {

    @Test
    @DisplayName("Deve garantir o funcionamento correto dos getters e setters da entidade MemoriaCalculo")
    void deveTestarEntidadeMemoriaCalculo() {
        Simulacao simulacaoMock = new Simulacao();

        MemoriaCalculo memoria = new MemoriaCalculo();
        memoria.setId(10L);
        memoria.setMes(1);
        memoria.setSaldoInicial(new BigDecimal("1000.00"));
        memoria.setJuroAplicado(new BigDecimal("104.08"));
        memoria.setValorFinal(new BigDecimal("1104.08"));
        memoria.setSimulacao(simulacaoMock);

        assertEquals(10L, memoria.getId());
        assertEquals(1, memoria.getMes());
        assertEquals(new BigDecimal("1000.00"), memoria.getSaldoInicial());
        assertEquals(new BigDecimal("104.08"), memoria.getJuroAplicado());
        assertEquals(new BigDecimal("1104.08"), memoria.getValorFinal());
        assertNotNull(memoria.getSimulacao());
    }
}
