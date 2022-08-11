package io.codekaffee.ifood.marketplace.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import io.codekaffee.ifood.marketplace.data.PratoDTO;
import io.codekaffee.ifood.marketplace.repositories.PratoRepository;
import io.smallrye.mutiny.Multi;

@Path(value = "/restaurants")
public class RestaurantResource {

    @Inject
    private PratoRepository repository;

    @GET
    @Path("/{id}/pratos")
    @APIResponse(responseCode = "200", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = PratoDTO.class)))
    public Multi<PratoDTO> findPratosByRestaurante(@PathParam("id") Long restaurantId) {
        return repository.findByRestauranteId(restaurantId);
    }
}
