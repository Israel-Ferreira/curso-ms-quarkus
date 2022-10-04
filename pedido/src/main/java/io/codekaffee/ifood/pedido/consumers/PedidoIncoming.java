package io.codekaffee.ifood.pedido.consumers;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.codekaffee.ifood.pedido.models.PedidoDTO;

@ApplicationScoped
public class PedidoIncoming {
    
    @Incoming("pedidos")
    public void consumePedidos(PedidoDTO pedidoDTO) {
        System.out.println(pedidoDTO);
    }
}
