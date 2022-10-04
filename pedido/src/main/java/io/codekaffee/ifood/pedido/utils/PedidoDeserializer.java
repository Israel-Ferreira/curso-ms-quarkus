package io.codekaffee.ifood.pedido.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.codekaffee.ifood.pedido.models.PedidoDTO;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PedidoDeserializer extends ObjectMapperDeserializer<PedidoDTO> {

    public PedidoDeserializer() {
        super(PedidoDTO.class);
        //TODO Auto-generated constructor stub
    }

    

}
