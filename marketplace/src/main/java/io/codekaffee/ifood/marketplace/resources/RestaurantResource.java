package io.codekaffee.ifood.marketplace.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import io.codekaffee.ifood.marketplace.data.PratoDTO;
import io.codekaffee.ifood.marketplace.repositories.PratoRepository;
import io.smallrye.mutiny.Multi;

@Path(value = "/restaurants")
public class RestaurantResource {

    @Inject
    private PratoRepository repository;

    @GET
    @Path("/{id}/pratos")
    public Multi<PratoDTO> findPratosByRestaurante(@PathParam("id") Long restaurantId) {
        return repository.findByRestauranteId(restaurantId);
    }
}
