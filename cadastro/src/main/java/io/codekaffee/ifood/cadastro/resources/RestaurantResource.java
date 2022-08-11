package io.codekaffee.ifood.cadastro.resources;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

import io.codekaffee.ifood.cadastro.dto.PratoDTO;
import io.codekaffee.ifood.cadastro.dto.RestauranteDTO;
import io.codekaffee.ifood.cadastro.dto.RestauranteViewDTO;
import io.codekaffee.ifood.cadastro.dto.UpdateRestauranteDTO;
import io.codekaffee.ifood.cadastro.models.Prato;
import io.codekaffee.ifood.cadastro.services.RestaurantService;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path(value = "/restaurantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("proprietario")
@SecurityScheme(
    securitySchemeName = "ifood-oauth", 
    type = SecuritySchemeType.OAUTH2, 
    flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/ifood/protocol/openid-connect/token") )
)
@SecurityRequirement(name = "ifood-oauth", scopes = {"proprietario"})
public class RestaurantResource {

    private final RestaurantService restaurantService;



    @Inject
    public RestaurantResource(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GET
    @Counted(name = "Quantidade Buscas de Restaurantes")
    @SimplyTimed(name = "Tempo de Busca dos Restaurantes")
    @Tag(name = "Restaurantes")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = RestauranteViewDTO.class))
            }),

            @APIResponse(responseCode = "500", description = "Erro ao lidar com a requisição")
    })
    public Response listRestaurants() {
        List<RestauranteViewDTO> restaurantes = restaurantService.listRestaurants();
        return Response.ok(restaurantes).build();
    }

    @POST
    @Tag(name = "Restaurantes")
    public Response criarRestaurante(RestauranteDTO restauranteDTO) {
        restaurantService.criarRestaurante(restauranteDTO);
        return Response.status(Status.CREATED).build();
    }

    @GET
    @Path(value = "{id}")
    @Tag(name = "Restaurantes")
    @APIResponseSchema(responseCode = "200", value = RestauranteViewDTO.class)
    public Response getById(@PathParam("id") Long id) {
        RestauranteViewDTO view = restaurantService.getRestaurantById(id);
        return Response.ok(view).build();
    }

    @PUT
    @Path(value = "{id}")
    @Tag(name = "Restaurantes")
    public Response updateById(@PathParam("id") Long id, UpdateRestauranteDTO dto) {
        restaurantService.updateRestaurant(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path(value = "{id}")
    @Tag(name = "Restaurantes")
    public Response deleteRestaurante(@PathParam("id") Long id) {
        return Response.noContent().build();
    }

    @GET
    @Path("{id}/pratos")
    @Operation(description = "Listar de um restaurante informado pelo id", summary = "Listar pratos")
    @Tags(value = {
            @Tag(name = "Prato"),
    })
    public Response listarPratos(@PathParam("id") Long id) {
        List<Prato> pratos = restaurantService.listarPratos(id);
        return Response.ok(pratos).build();
    }

    @POST
    @Path("{id}/pratos")
    @Operation(description = "Adiciona um prato a um restaurante informado pelo id", summary = "Adicionar Prato")
    @APIResponse(responseCode = "400", description = "Erro de validação ao criar o prato", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @APIResponse(responseCode = "201", description = "Prato Criado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @Tags(value = {
            @Tag(name = "Prato"),
    })
    public Response adicionarPratos(@PathParam("id") Long id, PratoDTO dto) {
        restaurantService.adicionarPratoAoRestaurante(id, dto);
        return Response.status(Status.CREATED).build();
    }

    @GET
    @Path("{id}/pratos/{pratoId}")
    @Tags(value = {
            @Tag(name = "Prato"),
    })
    public Response getPrato(@PathParam("id") Long id, @PathParam("pratoId") Long pratoId) {
        Prato prato = restaurantService.getPrato(id, pratoId);
        return Response.ok(prato).build();
    }

    @DELETE
    @Path("{id}/pratos/{pratoId}")
    @Tags(value = {
            @Tag(name = "Prato"),
    })
    public Response deletePrato(@PathParam("id") Long id, @PathParam("pratoId") Long pratoId) {
        restaurantService.deletePrato(id, pratoId);
        return Response.noContent().build();
    }

    @PUT
    @Transactional
    @Path("{id}/pratos/{pratoId}")
    @Tags(value = {
            @Tag(name = "Prato"),
    })
    public Response updatePrato(@PathParam("id") Long id, @PathParam("pratoId") Long pratoId, PratoDTO dto) {
        restaurantService.updatePrato(id, pratoId, dto);
        return Response.noContent().build();
    }

}
