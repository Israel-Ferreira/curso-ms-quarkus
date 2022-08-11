package io.codekaffee.ifood.cadastro.services;

import io.codekaffee.ifood.cadastro.models.Restaurante;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@ApplicationScoped
public class RestauranteProducer {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    @Inject
    @Channel("restaurantes")
    private Emitter<String> restaurantEmitter;


    public void sendRestauranteToQuoue(Restaurante restaurante){
        try(Jsonb jsonb = JsonbBuilder.create()) {
            String msg = jsonb.toJson(restaurante);

            restaurantEmitter.send(msg);
        } catch (Exception e) {
            logger.error("Erro ao enviar a msg. para o broker: {}", e.getMessage());
        }
    }


}
