package io.codekaffee.ifood.marketplace.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.codekaffee.ifood.marketplace.models.PratoCarrinho;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

@ApplicationScoped
public class PratoCarrinhoRepository {
    
    @Inject
    private PgPool pool;


    public Uni<String> saveCarrinho(String cliente, Long carrinhoId){
        return pool.preparedQuery("INSERT INTO prato_cliente (cliente, prato) VALUES ($1, $2) RETURNING (cliente)")
            .execute(Tuple.of(cliente, carrinhoId))
            .map(pgRowSet -> {
                return pgRowSet.iterator().next().getString("cliente");
            });
    }


    
    public Uni<List<PratoCarrinho>> findCarrinho(String client){
        return pool.preparedQuery("select * from prato_cliente where cliente = $1 ")
            .execute(Tuple.of(client))
            .map((t) -> {
                List<PratoCarrinho> carrinhos =  new ArrayList<>(t.size());
                for (Row row : t) {
                    carrinhos.add(toPratoCarrinho(row));
                }

                return carrinhos;
            });
    }


    private static PratoCarrinho toPratoCarrinho(Row row) {
        PratoCarrinho pc = new PratoCarrinho();
        pc.setUsuario(row.getString("cliente"));
        pc.setPrato(row.getLong("prato"));
        return pc;
    }

    public Uni<Boolean> delete(String cliente) {
        return pool.preparedQuery("DELETE FROM prato_cliente WHERE cliente = $1").execute(Tuple.of(cliente))
                .map(pgRowSet -> pgRowSet.rowCount() == 1);

    }
    
}
