package io.codekaffee.ifood.cadastro.resources;

import io.codekaffee.ifood.cadastro.dto.RestauranteDTO;
import io.codekaffee.ifood.cadastro.models.Restaurante;
import io.codekaffee.ifood.cadastro.repositories.PratoRepository;
import io.codekaffee.ifood.cadastro.repositories.RestauranteRepository;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path(value = "/restaurantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestaurantResource {

    private final PratoRepository pratoRepository;
    private final RestauranteRepository repository;

    @Inject
    public RestaurantResource(PratoRepository pratoRepository, RestauranteRepository restauranteRepository) {
        this.pratoRepository = pratoRepository;
        this.repository = restauranteRepository;
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listRestaurants() {
        List<Restaurante> restaurantes =  repository.findAll().list();
        return Response.ok(restaurantes).build();
    }



    @POST
    public Response criarRestaurante(RestauranteDTO restauranteDTO) {

        Restaurante restaurante = new Restaurante(
        );

        return Response.status(Status.CREATED).build();
    }

    

    @GET
    @Path(value = "{id}")
    public Response getById(@PathParam("id") Long id) {
        Optional<Restaurante> restaurante = repository.findByIdOptional(id);

        if(restaurante.isEmpty()){
            return Response.status(Status.NOT_FOUND).build();
        }
        
        return Response.ok(restaurante.get()).build();
    }



}
