package io.codekaffee.ifood.cadastro.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

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

@ApplicationScoped
public class RestaurantService {

    private final PratoRepository pratoRepository;
    private final RestauranteRepository repository;
    private final LocalizacaoRepository localizacaoRepository;
    private final Validator validator;

    @Inject
    public RestaurantService(
            PratoRepository pratoRepository,
            RestauranteRepository repository,
            LocalizacaoRepository localizacaoRepository,
            Validator validator) {
        this.pratoRepository = pratoRepository;
        this.repository = repository;
        this.localizacaoRepository = localizacaoRepository;
        this.validator = validator;
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
    public void criarRestaurante(RestauranteDTO restauranteDTO) {
        var violations = validator.validate(restauranteDTO);

        if (!violations.isEmpty()) {
            // List<String> errors = violations.stream().map(err -> err.getMessage())
            //         .collect(Collectors.toList());

            throw new BadRequestException();
        }

        Restaurante restaurante = restauranteDTO.toModel();

        localizacaoRepository.persist(restaurante.getLocalizacao());

        repository.persist(restaurante);
    }


    @Transactional
    public void updateRestaurant(Long id, UpdateRestauranteDTO dto) {
        Restaurante restauranteDb = repository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);

        var violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            List<String> errors = violations.stream().map(err -> err.getMessage())
                    .collect(Collectors.toList());

            throw new ValidationException(Arrays.toString(errors.toArray()));

        }

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
