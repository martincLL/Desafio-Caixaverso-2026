package org.caixa.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_memoriacalculo")
public class MemoriaCalculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "mes", nullable = false)
    private Integer mes;

    @Column(name = "saldo_inicial", precision = 15, scale = 2, nullable = false)
    private BigDecimal saldoInicial;

    @Column(name = "juro_aplicado", precision = 15, scale = 2, nullable = false)
    private BigDecimal juroAplicado;

    @Column(name = "valor_final", precision = 15, scale = 2, nullable = false)
    private BigDecimal valorFinal;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulacao_id", nullable = false)
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
