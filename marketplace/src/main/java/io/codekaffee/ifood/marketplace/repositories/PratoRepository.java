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
import io.vertx.mutiny.sqlclient.Tuple;

@ApplicationScoped
public class PratoRepository {

    @Inject
    private PgPool pool;

    public Multi<PratoDTO> findAll() {
        Uni<RowSet<Row>> query = pool.preparedQuery("select * from prato").execute();

        return query.onItem().transformToMulti(row -> Multi.createFrom().iterable(row))
            .onItem().transform(PratoDTO::from);

    }


    public Uni<PratoDTO> findById(Long id){
        Uni<RowSet<Row>> query =  pool.preparedQuery("select * from prato where prato.id = $1 order by nome asc").execute(Tuple.of(id));
        return query.onItem()
            .transform(RowSet::iterator)
            .onItem()
            .transform(iterator -> iterator.hasNext() ? PratoDTO.from(iterator.next()) : null);

    }


    public Multi<PratoDTO> findByRestauranteId(Long id){
        String query = "select * from prato where prato.restaurante_id = $1 order by nome asc";
        Uni<RowSet<Row>> pq = pool.preparedQuery(query).execute(Tuple.of(id));

        return pq.onItem().transformToMulti(row -> Multi.createFrom().iterable(row))
            .onItem().transform(PratoDTO::from);
    }
}
