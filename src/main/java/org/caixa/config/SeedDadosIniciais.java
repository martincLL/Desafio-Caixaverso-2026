package org.caixa.config;

import io.quarkus.arc.profile.UnlessBuildProfile;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.caixa.dtos.SimulacaoRequestDTO;
import org.caixa.services.SimulacaoService;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
@UnlessBuildProfile("test")
public class SeedDadosIniciais {

    @Inject
    SimulacaoService simulacaoService;

    void realizarSeedDadosAoIniciar(@Observes StartupEvent event) {
        if(simulacaoService.buscarTodasSimulacoes().isEmpty()) {
            List<SimulacaoRequestDTO> dadosIniciais = List.of(
                    new SimulacaoRequestDTO(new BigDecimal("5500.00"), new BigDecimal("1.5"), 12),
                    new SimulacaoRequestDTO(new BigDecimal("45000.00"), new BigDecimal("1.2"), 36),
                    new SimulacaoRequestDTO(new BigDecimal("1500.00"), new BigDecimal("2.5"), 1),
                    new SimulacaoRequestDTO(new BigDecimal("150000.00"), new BigDecimal("0.8"), 90),
                    new SimulacaoRequestDTO(new BigDecimal("800.00"), new BigDecimal("3.0"), 9)

            );

            for(SimulacaoRequestDTO requestDTO : dadosIniciais) {
                simulacaoService.simular(requestDTO);
            }
        }
    }
}
