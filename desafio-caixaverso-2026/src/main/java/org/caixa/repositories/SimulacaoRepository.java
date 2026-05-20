package org.caixa.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.caixa.entities.Simulacao;

@ApplicationScoped
public class SimulacaoRepository implements PanacheRepository<Simulacao> {
}
