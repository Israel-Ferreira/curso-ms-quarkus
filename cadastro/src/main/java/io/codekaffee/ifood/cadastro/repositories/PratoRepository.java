package io.codekaffee.ifood.cadastro.repositories;

import io.codekaffee.ifood.cadastro.models.Prato;
import io.codekaffee.ifood.cadastro.models.Restaurante;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PratoRepository implements PanacheRepository<Prato> {

    public List<Prato> findPratosByRestaurante(Restaurante restaurante) {
        return list("restaurante = ?1", restaurante);
    }
}
