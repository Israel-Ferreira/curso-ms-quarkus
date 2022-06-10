package io.codekaffee.ifood.cadastro.repositories;

import javax.enterprise.context.ApplicationScoped;

import io.codekaffee.ifood.cadastro.models.Localizacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class LocalizacaoRepository implements PanacheRepository<Localizacao> {
    
}
