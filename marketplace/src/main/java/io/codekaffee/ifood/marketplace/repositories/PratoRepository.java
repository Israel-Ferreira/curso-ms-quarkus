package io.codekaffee.ifood.marketplace.repositories;

import java.util.Set;
import java.util.stream.StreamSupport;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.codekaffee.ifood.marketplace.data.PratoDTO;
import io.codekaffee.ifood.marketplace.models.Prato;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.PreparedQuery;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

@ApplicationScoped
public class PratoRepository {

    @Inject
    private PgPool pool;

    public Multi<PratoDTO> findAll() {
        Uni<RowSet<Row>> query = pool.preparedQuery("select * from prato").execute();

        return query.onItem().transformToMulti(row -> Multi.createFrom().iterable(row))
            .onItem().transform(PratoDTO::from);

    }
}
