package io.codekaffee.ifood.marketplace.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import io.codekaffee.ifood.marketplace.data.PratoDTO;
import io.codekaffee.ifood.marketplace.repositories.PratoRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.opentracing.Traced;

@Traced
@Path("/pratos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PratoResource {


    @Inject
    private PratoRepository repository;


    @GET
    @APIResponse(responseCode = "200", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = PratoDTO.class)))
    public Multi<PratoDTO> buscarPratos() {
        return repository.findAll();
    }


    @GET
    @Path("{id}")
    @APIResponse(responseCode = "200", content = @Content(schema = @Schema(type = SchemaType.OBJECT, implementation = PratoDTO.class)))
    public Uni<PratoDTO> findPratoById(@PathParam(value = "id") Long id){
        return repository.findById(id);
    }
}
