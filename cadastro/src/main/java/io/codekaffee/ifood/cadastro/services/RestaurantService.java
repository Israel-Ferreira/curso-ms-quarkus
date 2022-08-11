package io.codekaffee.ifood.cadastro.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.opentracing.Traced;

import io.codekaffee.ifood.cadastro.dto.PratoDTO;
import io.codekaffee.ifood.cadastro.dto.RestauranteDTO;
import io.codekaffee.ifood.cadastro.dto.RestauranteViewDTO;
import io.codekaffee.ifood.cadastro.dto.UpdateRestauranteDTO;
import io.codekaffee.ifood.cadastro.exceptions.ValidationException;
import io.codekaffee.ifood.cadastro.models.Prato;
import io.codekaffee.ifood.cadastro.models.Restaurante;
import io.codekaffee.ifood.cadastro.repositories.LocalizacaoRepository;
import io.codekaffee.ifood.cadastro.repositories.PratoRepository;
import io.codekaffee.ifood.cadastro.repositories.RestauranteRepository;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logmanager.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Slf4jLogger;

@Traced
@ApplicationScoped
public class RestaurantService {

    private static final Logger logger = LoggerFactory.getLogger(Slf4jLogger.class);

    private final PratoRepository pratoRepository;
    private final RestauranteRepository repository;
    private final LocalizacaoRepository localizacaoRepository;


    @Channel("restaurants")
    private final Emitter<String> emitter;

    @Inject
    public RestaurantService(
            PratoRepository pratoRepository,
            RestauranteRepository repository,
            LocalizacaoRepository localizacaoRepository,
            Emitter<String> emitter) {
        this.pratoRepository = pratoRepository;
        this.repository = repository;
        this.localizacaoRepository = localizacaoRepository;
        this.emitter = emitter;
    }

    public List<RestauranteViewDTO> listRestaurants() {
        return repository.findAll().list()
                .stream().map(RestauranteViewDTO::new)
                .collect(Collectors.toList());
    }

    public RestauranteViewDTO getRestaurantById(Long id) {
        Restaurante restaurante = repository.findByIdOptional(id).orElseThrow(NotFoundException::new);
        return new RestauranteViewDTO(restaurante);
    }


    @Transactional
    public void criarRestaurante(@Valid RestauranteDTO restauranteDTO) {
        
        repository.find("cnpj", restauranteDTO.getCnpj())
            .firstResultOptional()
            .ifPresent(rest -> {
                throw new ValidationException("CNPJ Já existente");
            });


        Restaurante restaurante = restauranteDTO.toModel();

        localizacaoRepository.persist(restaurante.getLocalizacao());

        repository.persist(restaurante);


        try (Jsonb json = JsonbBuilder.create()){
            String msg = json.toJson(restaurante);
            emitter.send(msg);
        } catch (Exception e) {
            logger.error("Erro ao enviar a msg. para o broker: {}", e.getMessage());
        }
    }


    @Transactional
    public void updateRestaurant(Long id, @Valid UpdateRestauranteDTO dto) {
        Restaurante restauranteDb = repository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);


        restauranteDb.setNome(dto.getNome());

        repository.persist(restauranteDb);
    }

    @Transactional
    public void adicionarPratoAoRestaurante(Long restaurantId, PratoDTO dto) {
        Restaurante restaurante = repository.findByIdOptional(restaurantId)
                .orElseThrow(NotFoundException::new);

        Prato prato = new Prato(
                dto.getNome(),
                dto.getDescricao(),
                dto.getPreco(),
                restaurante);

        pratoRepository.persist(prato);
    }

    @Transactional
    public void deletePrato(Long restaurantId, Long pratoId) {
        Optional<Restaurante> restaurante = repository.findByIdOptional(restaurantId);

        if (restaurante.isEmpty()) {
            throw new NotFoundException();
        }

        pratoRepository.deleteById(pratoId);
    }

    public Prato getPrato(Long restaurantId, Long pratoId) {
        Optional<Restaurante> restaurante = repository.findByIdOptional(restaurantId);

        if (restaurante.isEmpty()) {
            throw new NotFoundException();
        }

        return pratoRepository.findByIdOptional(pratoId)
                .orElseThrow(NotFoundException::new);
    }

    public List<Prato> listarPratos(Long restaurantId) {

        Restaurante restaurante = repository.findByIdOptional(restaurantId)
                .orElseThrow(NotFoundException::new);

        return pratoRepository.findPratosByRestaurante(restaurante);
    }


    public void updatePrato(Long restaurantId, Long pratoId, PratoDTO dto){
        Optional<Restaurante> restaurante = repository.findByIdOptional(restaurantId);

        if (restaurante.isEmpty()) {
            throw new NotFoundException();
        }

        pratoRepository.findByIdOptional(pratoId)
                .ifPresentOrElse(prato -> {
                    // prato.setNome(dto.getNome());
                    // prato.setDescricao(dto.getDescricao());

                    prato.setPreco(dto.getPreco());

                    pratoRepository.persist(prato);

                }, () -> {
                    throw new NotFoundException();
                });

    }
}
