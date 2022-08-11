package io.codekaffee.ifood.marketplace.consumers;

import io.codekaffee.ifood.marketplace.models.Restaurante;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@ApplicationScoped
public class MarketplaceConsumer {

    @Incoming("restaurantes")
    public void consumeRestauranteQueueMessages(String json) {


        try(Jsonb create = JsonbBuilder.create()) {
            Restaurante restaurante  = create.fromJson(json, Restaurante.class);


            System.out.println(json);
            System.out.println(restaurante);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
