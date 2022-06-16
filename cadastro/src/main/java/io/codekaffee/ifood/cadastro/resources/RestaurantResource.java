package io.codekaffee.ifood.cadastro.resources;

import io.codekaffee.ifood.cadastro.dto.PratoDTO;
import io.codekaffee.ifood.cadastro.dto.RestauranteDTO;
import io.codekaffee.ifood.cadastro.dto.RestauranteViewDTO;
import io.codekaffee.ifood.cadastro.models.Prato;
import io.codekaffee.ifood.cadastro.models.Restaurante;
import io.codekaffee.ifood.cadastro.repositories.LocalizacaoRepository;
import io.codekaffee.ifood.cadastro.repositories.PratoRepository;
import io.codekaffee.ifood.cadastro.repositories.RestauranteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path(value = "/restaurantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RestaurantResource {

    private final PratoRepository pratoRepository;
    private final RestauranteRepository repository;
    private final LocalizacaoRepository localizacaoRepository;

    @Inject
    public RestaurantResource(
        PratoRepository pratoRepository,
        RestauranteRepository restauranteRepository,
        LocalizacaoRepository localizacaoRepository
    ) {
        this.pratoRepository = pratoRepository;
        this.repository = restauranteRepository;
        this.localizacaoRepository = localizacaoRepository;
    }

    @GET
    @Tag(name = "Restaurantes")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
        @APIResponse(
            responseCode = "200",
            content = {
                @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = RestauranteViewDTO.class))
            }
        ),

        @APIResponse(
            responseCode = "500",
            description = "Erro ao lidar com a requisição"
        )
    })
    public Response listRestaurants() {
        List<RestauranteViewDTO> restaurantes = repository.findAll().list()
            .stream().map(RestauranteViewDTO::new)
            .collect(Collectors.toList());


        return Response.ok(restaurantes).build();
    }

    @POST
    @Transactional
    @Tag(name = "Restaurantes")
    public Response criarRestaurante(RestauranteDTO restauranteDTO) {
        Restaurante restaurante = restauranteDTO.toModel();

        localizacaoRepository.persist(restaurante.getLocalizacao());

        repository.persist(restaurante);

        return Response.status(Status.CREATED).build();
    }

    @GET
    @Path(value = "{id}")
    @Tag(name = "Restaurantes")
    @APIResponseSchema(responseCode = "200", value = RestauranteViewDTO.class)
    public Response getById(@PathParam("id") Long id) {
        Optional<Restaurante> restaurante = repository.findByIdOptional(id);

        if (restaurante.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        }


        RestauranteViewDTO view = new RestauranteViewDTO(restaurante.get());

        return Response.ok(view).build();
    }

    @PUT
    @Transactional
    @Path(value = "{id}")
    @Tag(name = "Restaurantes")
    public Response updateById(@PathParam("id") Long id, RestauranteDTO dto) {
        Restaurante restauranteDb = repository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);

        restauranteDb.setNome(restauranteDb.getNome());

        repository.persist(restauranteDb);

        return Response.noContent().build();
    }

    @DELETE
    @Transactional
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
        Restaurante restaurante = repository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);

        List<Prato> pratos = pratoRepository.findPratosByRestaurante(restaurante);
        return Response.ok(pratos).build();
    }

    @POST
    @Transactional
    @Path("{id}/pratos")
    @Operation(description = "Adiciona um prato a um restaurante informado pelo id", summary = "Adicionar Prato")
    @APIResponse(responseCode = "400", description = "Erro de validação ao criar o prato", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @APIResponse(responseCode = "201", description = "Prato Criado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @Tags(value = {
            @Tag(name = "Prato"),
    })
    public Response adicionarPratos(@PathParam("id") Long id, PratoDTO dto) {
        Restaurante restaurante = repository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);

        Prato prato = new Prato(
                dto.getNome(),
                dto.getDescricao(),
                dto.getPreco(),
                restaurante);

        pratoRepository.persist(prato);

        return Response.status(Status.CREATED).build();
    }

    @GET
    @Path("{id}/pratos/{pratoId}")
    @Tags(value = {
            @Tag(name = "Prato"),
    })
    public Response getPrato(@PathParam("id") Long id, @PathParam("pratoId") Long pratoId) {
        Optional<Restaurante> restaurante = repository.findByIdOptional(id);

        if (restaurante.isEmpty()) {
            throw new NotFoundException();
        }

        Prato prato = pratoRepository.findByIdOptional(pratoId)
                .orElseThrow(NotFoundException::new);

        return Response.ok(prato).build();
    }

    @DELETE
    @Transactional
    @Path("{id}/pratos/{pratoId}")
    @Tags(value = {
            @Tag(name = "Prato"),
    })
    public Response deletePrato(@PathParam("id") Long id, @PathParam("pratoId") Long pratoId) {
        Optional<Restaurante> restaurante = repository.findByIdOptional(id);

        if (restaurante.isEmpty()) {
            throw new NotFoundException();
        }

        pratoRepository.deleteById(pratoId);

        return Response.noContent().build();
    }

    @PUT
    @Transactional
    @Path("{id}/pratos/{pratoId}")
    @Tags(value = {
            @Tag(name = "Prato"),
    })
    public Response updatePrato(@PathParam("id") Long id, @PathParam("pratoId") Long pratoId, PratoDTO dto) {
        Optional<Restaurante> restaurante = repository.findByIdOptional(id);

        if (restaurante.isEmpty()) {
            throw new NotFoundException();
        }

        pratoRepository.findByIdOptional(id)
                .ifPresentOrElse(prato -> {
                    // prato.setNome(dto.getNome());
                    // prato.setDescricao(dto.getDescricao());

                    prato.setPreco(dto.getPreco());

                    pratoRepository.persist(prato);

                }, () -> {
                    throw new NotFoundException();
                });

        return Response.noContent().build();
    }

}
