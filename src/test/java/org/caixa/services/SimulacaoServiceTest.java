package org.caixa.services;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.caixa.dtos.SimulacaoRequestDTO;
import org.caixa.dtos.SimulacaoResponseDTO;
import org.caixa.entities.Simulacao;
import org.caixa.exceptions.SimulacaoNaoEncontradaException;
import org.caixa.repositories.SimulacaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class SimulacaoServiceTest {

    @Inject
    SimulacaoService simulacaoService;

    @InjectMock
    SimulacaoRepository simulacaoRepository;

    @Test
    @DisplayName("Deve retornar uma lista com todas as simulações cadastradas")
    void deveBuscarTodasSimulacoes() {
        Simulacao simulacaoMock = new Simulacao();
        simulacaoMock.setId(15L);
        simulacaoMock.setValorInicial(new BigDecimal("1000.00"));
        simulacaoMock.setTaxaJurosMensal(new BigDecimal("2.0"));
        simulacaoMock.setPrazoMeses(5);
        simulacaoMock.setValorFinal(new BigDecimal("1104.08"));
        simulacaoMock.setValorTotalJuros(new BigDecimal("104.08"));
        simulacaoMock.setMemoriaCalculo(new ArrayList<>());

        Mockito.when(simulacaoRepository.listAll()).thenReturn(List.of(simulacaoMock));

        List<SimulacaoResponseDTO> responseDTO = simulacaoService.buscarTodasSimulacoes();

        assertNotNull(responseDTO);
        assertEquals(1, responseDTO.size());
        assertEquals(15L, responseDTO.get(0).id());
        assertEquals(new BigDecimal("1104.08"), responseDTO.get(0).valorTotalFinal());
        assertEquals(new BigDecimal("104.08"), responseDTO.get(0).valorTotalJuros());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não houver nenhuma simulação cadastrada")
    void deveRetornarListaVaziaAoBuscarTodasSimulacoes() {
        Mockito.when(simulacaoRepository.listAll()).thenReturn(Collections.emptyList());

        List<SimulacaoResponseDTO> responseDTO = simulacaoService.buscarTodasSimulacoes();

        assertNotNull(responseDTO);
        assertTrue(responseDTO.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar simulação com o id passado nos parâmetros corretamente")
    void deveBuscarSimulacaoPorIdSucesso() {
        Simulacao simulacaoMock = new Simulacao();
        simulacaoMock.setId(15L);
        simulacaoMock.setValorInicial(new BigDecimal("1000.00"));
        simulacaoMock.setTaxaJurosMensal(new BigDecimal("2.0"));
        simulacaoMock.setPrazoMeses(5);
        simulacaoMock.setValorFinal(new BigDecimal("1104.08"));
        simulacaoMock.setValorTotalJuros(new BigDecimal("104.08"));
        simulacaoMock.setMemoriaCalculo(new ArrayList<>());

        Mockito.when(simulacaoRepository.findByIdOptional(15L)).thenReturn(Optional.of(simulacaoMock));

        SimulacaoResponseDTO responseDTO = simulacaoService.buscarSimulacaoPorId(15L);

        assertNotNull(responseDTO);
        assertEquals(15L, responseDTO.id());
        assertEquals(new BigDecimal("1104.08"), responseDTO.valorTotalFinal());
        assertEquals(new BigDecimal("104.08"), responseDTO.valorTotalJuros());
    }

    @Test
    @DisplayName("Deve lançar a exceção SimulacaoNaoEncontradaException ao buscar uma simulação pelo id inexistente")
    void deveLancarExcecaoAoBuscarIdInexistente() {
        Mockito.when(simulacaoRepository.findByIdOptional(77L))
                .thenReturn(Optional.empty());

        SimulacaoNaoEncontradaException e = assertThrows(SimulacaoNaoEncontradaException.class, () -> {
            simulacaoService.buscarSimulacaoPorId(77L);
        });

        assertEquals("Simulação não encontrada", e.getMessage());
    }

    @Test
    @DisplayName("Deve criar uma simulação de 4 meses e aplicar corretamente os juros compostos sobre o valor investido em cada mês")
    void deveCriarSimulacaoSucesso() {
        SimulacaoRequestDTO requestDTO = new SimulacaoRequestDTO(
                new BigDecimal("5000.00"),
                new BigDecimal("1.30"),
                4
        );
        Mockito.doNothing().when(simulacaoRepository).persist(any(Simulacao.class));
        SimulacaoResponseDTO responseDTO = simulacaoService.simular(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(4, responseDTO.memoriaCalculo().size());
        assertEquals(new BigDecimal("5265.12"), responseDTO.valorTotalFinal());
        assertEquals(new BigDecimal("265.12"), responseDTO.valorTotalJuros());
    }

    @Test
    @DisplayName("Deve criar simulação para o prazo mínimo exigido de 1 mês (cenário de borda)")
    void deveCriarSimulacaoPrazoMinimo() {
        SimulacaoRequestDTO requestDTO = new SimulacaoRequestDTO(
                new BigDecimal("3500.00"),
                new BigDecimal("1.8"),
                1
        );
        Mockito.doNothing().when(simulacaoRepository).persist(any(Simulacao.class));
        SimulacaoResponseDTO responseDTO = simulacaoService.simular(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(1, responseDTO.memoriaCalculo().size());
        assertEquals(new BigDecimal("3563.00"), responseDTO.valorTotalFinal());
        assertEquals(new BigDecimal("63.00"), responseDTO.valorTotalJuros());
    }
}
