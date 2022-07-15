package io.codekaffee.ifood.marketplace.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.codekaffee.ifood.marketplace.data.PratoDTO;
import io.codekaffee.ifood.marketplace.models.Prato;
import io.codekaffee.ifood.marketplace.repositories.PratoRepository;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;

@Path("/pratos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PratoResource {


    @Inject
    private PratoRepository repository;



    @GET
    public Multi<PratoDTO> buscarPratos() {
        return repository.findAll();
    }
}
