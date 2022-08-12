package io.codekaffee.ifood.marketplace.consumers;

import io.codekaffee.ifood.marketplace.models.Restaurante;
import io.codekaffee.ifood.marketplace.repositories.PratoRepository;
import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;


@Traced
@ApplicationScoped
public class MarketplaceConsumer {

    @Inject
    private PratoRepository repository;

    @Blocking
    @Incoming("restaurantes")
    public void consumeRestauranteQueueMessages(String json) {
        try(Jsonb create = JsonbBuilder.create()) {
            Restaurante restaurante  = create.fromJson(json, Restaurante.class);

            System.out.println(json);
            System.out.println(restaurante);

            repository.persist(restaurante);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
