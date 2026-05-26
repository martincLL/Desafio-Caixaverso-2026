package org.caixa.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_simulacao")
public class Simulacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_simulacao", updatable = false, nullable = false)
    private Long id;

    @Column(name = "valor_inicial", precision = 15, scale = 2, nullable = false)
    private BigDecimal valorInicial;

    @Column(name = "taxa_juros_mensal", precision = 15, scale = 4, nullable = false)
    private BigDecimal taxaJurosMensal;

    @Column(name = "prazo_meses", nullable = false)
    private Integer prazoMeses;

    @Column(name = "valor_final", precision = 15, scale = 2)
    private BigDecimal valorFinal;

    @Column(name = "valor_total_juros", precision = 15, scale = 2)
    private BigDecimal valorTotalJuros;

    @OneToMany(mappedBy = "simulacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemoriaCalculo> memoriaCalculo = new ArrayList<>();

    public Simulacao() {
    }

    public Simulacao(Long id, BigDecimal valorInicial, BigDecimal taxaJurosMensal, Integer prazoMeses, BigDecimal valorFinal, BigDecimal valorTotalJuros, List<MemoriaCalculo> memoriaCalculo) {
        this.id = id;
        this.valorInicial = valorInicial;
        this.taxaJurosMensal = taxaJurosMensal;
        this.prazoMeses = prazoMeses;
        this.valorFinal = valorFinal;
        this.valorTotalJuros = valorTotalJuros;
        this.memoriaCalculo = memoriaCalculo;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValorInicial() {
        return valorInicial;
    }

    public BigDecimal getTaxaJurosMensal() {
        return taxaJurosMensal;
    }

    public Integer getPrazoMeses() {
        return prazoMeses;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public BigDecimal getValorTotalJuros() {
        return valorTotalJuros;
    }

    public List<MemoriaCalculo> getMemoriaCalculo() {
        return memoriaCalculo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValorInicial(BigDecimal valorInicial) {
        this.valorInicial = valorInicial;
    }

    public void setTaxaJurosMensal(BigDecimal taxaJurosMensal) {
        this.taxaJurosMensal = taxaJurosMensal;
    }

    public void setPrazoMeses(Integer prazoMeses) {
        this.prazoMeses = prazoMeses;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

    public void setValorTotalJuros(BigDecimal valorTotalJuros) {
        this.valorTotalJuros = valorTotalJuros;
    }

    public void setMemoriaCalculo(List<MemoriaCalculo> memoriaCalculo) {
        this.memoriaCalculo = memoriaCalculo;
    }
}
