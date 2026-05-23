package org.caixa.entities;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class SimulacaoTest {

    @Test
    @DisplayName("Deve garantir o funcionamento correto dos getters e setters da entidade Simulação")
    void deveTestarEnidadeSimulacao() {
        Simulacao simulacao = new Simulacao();
        simulacao.setId(1L);
        simulacao.setValorInicial(new BigDecimal("1000.00"));
        simulacao.setTaxaJurosMensal(new BigDecimal("2.0"));
        simulacao.setPrazoMeses(5);
        simulacao.setValorFinal(new BigDecimal("1104.08"));
        simulacao.setValorTotalJuros(new BigDecimal("104.08"));
        simulacao.setMemoriaCalculo(new ArrayList<>());

        assertEquals(1L, simulacao.getId());
        assertEquals(new BigDecimal("1000.00"), simulacao.getValorInicial());
        assertEquals(new BigDecimal("2.0"), simulacao.getTaxaJurosMensal());
        assertEquals(5, simulacao.getPrazoMeses());
        assertEquals(new BigDecimal("1104.08"), simulacao.getValorFinal());
        assertEquals(new BigDecimal("104.08"), simulacao.getValorTotalJuros());
        assertNotNull(simulacao.getMemoriaCalculo());
    }
}
