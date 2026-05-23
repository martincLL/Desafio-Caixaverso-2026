package org.caixa.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.caixa.dtos.MemoriaCalculoDTO;
import org.caixa.dtos.SimulacaoRequestDTO;
import org.caixa.dtos.SimulacaoResponseDTO;
import org.caixa.entities.MemoriaCalculo;
import org.caixa.entities.Simulacao;
import org.caixa.exceptions.SimulacaoNaoEncontradaException;
import org.caixa.repositories.SimulacaoRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SimulacaoService {

    @Inject
    SimulacaoRepository simulacaoRepository;

    public List<SimulacaoResponseDTO> buscarTodasSimulacoes() {
        List<Simulacao> simulacoes = simulacaoRepository.listAll();
        return simulacoes.stream()
                .map(this::entidadeParaDTO)
                .toList();
    }

    public SimulacaoResponseDTO buscarSimulacaoPorId(Long id) {
        Simulacao simulacao = simulacaoRepository.findByIdOptional(id).orElseThrow(() -> new SimulacaoNaoEncontradaException("Simulação não encontrada"));
        return entidadeParaDTO(simulacao);
    }

    @Transactional
    public SimulacaoResponseDTO simular(SimulacaoRequestDTO requestDTO) {

        Simulacao simulacao = new Simulacao();
        simulacao.setValorInicial(requestDTO.valorInicial());
        simulacao.setTaxaJurosMensal(requestDTO.taxaJurosMensal());
        simulacao.setPrazoMeses(requestDTO.prazoMeses());

        List<MemoriaCalculoDTO> listaMemoriaCalculoDTO = gerarMemoriaDeCalculo(simulacao, requestDTO);
        simulacaoRepository.persist(simulacao);

        return  new SimulacaoResponseDTO(
                simulacao.getId(),
                simulacao.getValorFinal(),
                simulacao.getValorTotalJuros(),
                listaMemoriaCalculoDTO
        );
    }

    private List<MemoriaCalculoDTO> gerarMemoriaDeCalculo(Simulacao simulacao, SimulacaoRequestDTO requestDTO) {
        BigDecimal saldoAtual = requestDTO.valorInicial();
        BigDecimal taxaJurosDecimal = requestDTO.taxaJurosMensal().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        BigDecimal acumuladorJuros = BigDecimal.ZERO;

        List<MemoriaCalculo> listaMemoriaCalculo = new ArrayList<>();
        List<MemoriaCalculoDTO> listaMemoriaCalculoDTO = new ArrayList<>();

        for(int mes = 1; mes <= requestDTO.prazoMeses(); mes++) {
            BigDecimal saldoInicialMes = saldoAtual;
            BigDecimal juroMes = saldoInicialMes.multiply(taxaJurosDecimal).setScale(2, RoundingMode.HALF_UP);
            saldoAtual = saldoInicialMes.add(juroMes);
            acumuladorJuros = acumuladorJuros.add(juroMes);
            listaMemoriaCalculo.add(memoriaCalculoEntidade(mes, saldoInicialMes, juroMes, saldoAtual, simulacao));
            listaMemoriaCalculoDTO.add(new MemoriaCalculoDTO(mes, saldoInicialMes, juroMes, saldoAtual));
        }
        simulacao.setValorFinal(saldoAtual);
        simulacao.setValorTotalJuros(acumuladorJuros);
        simulacao.setMemoriaCalculo(listaMemoriaCalculo);

        return listaMemoriaCalculoDTO;
    }


    private MemoriaCalculo memoriaCalculoEntidade(Integer mes, BigDecimal saldoInicial, BigDecimal juros, BigDecimal saldoFinal, Simulacao simulacao) {
        MemoriaCalculo memoriaCalculo = new MemoriaCalculo();
        memoriaCalculo.setMes(mes);
        memoriaCalculo.setSaldoInicial(saldoInicial);
        memoriaCalculo.setJuroAplicado(juros);
        memoriaCalculo.setValorFinal(saldoFinal);
        memoriaCalculo.setSimulacao(simulacao);
        return memoriaCalculo;
    }

    private SimulacaoResponseDTO entidadeParaDTO(Simulacao simulacao) {
        List<MemoriaCalculoDTO> memoriasDTO = simulacao.getMemoriaCalculo().stream()
                .map(memoria -> new MemoriaCalculoDTO(
                        memoria.getMes(),
                        memoria.getSaldoInicial(),
                        memoria.getJuroAplicado(),
                        memoria.getValorFinal()
                )).toList();

        return new SimulacaoResponseDTO(
                simulacao.getId(),
                simulacao.getValorFinal(),
                simulacao.getValorTotalJuros(),
                memoriasDTO
        );
    }
}
