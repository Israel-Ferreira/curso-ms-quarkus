package io.codekaffee.ifood.cadastro.repositories;

import io.codekaffee.ifood.cadastro.models.Restaurante;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RestauranteRepository implements PanacheRepository<Restaurante> {
}
