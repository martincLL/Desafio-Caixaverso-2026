package org.caixa.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class MemoriaCalculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer mes;
    private BigDecimal saldoInicial;
    private BigDecimal juroAplicado;
    private BigDecimal valorFinal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulacao_id")
    private Simulacao simulacao;

    public MemoriaCalculo() {
    }

    public MemoriaCalculo(Long id, Integer mes, BigDecimal saldoInicial, BigDecimal juroAplicado, BigDecimal valorFinal, Simulacao simulacao) {
        this.id = id;
        this.mes = mes;
        this.saldoInicial = saldoInicial;
        this.juroAplicado = juroAplicado;
        this.valorFinal = valorFinal;
        this.simulacao = simulacao;
    }

    public Long getId() {
        return id;
    }

    public Integer getMes() {
        return mes;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public BigDecimal getJuroAplicado() {
        return juroAplicado;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public Simulacao getSimulacao() {
        return simulacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public void setJuroAplicado(BigDecimal juroAplicado) {
        this.juroAplicado = juroAplicado;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

    public void setSimulacao(Simulacao simulacao) {
        this.simulacao = simulacao;
    }
}
