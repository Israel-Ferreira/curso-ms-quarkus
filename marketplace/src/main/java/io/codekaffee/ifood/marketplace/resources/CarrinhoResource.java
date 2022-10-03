package io.codekaffee.ifood.marketplace.resources;


import io.codekaffee.ifood.marketplace.data.PedidoDTO;
import io.codekaffee.ifood.marketplace.data.PratoDTO;
import io.codekaffee.ifood.marketplace.data.PratoPedidoDTO;
import io.codekaffee.ifood.marketplace.data.RestauranteDTO;
import io.codekaffee.ifood.marketplace.models.PratoCarrinho;
import io.codekaffee.ifood.marketplace.models.Restaurante;
import io.codekaffee.ifood.marketplace.repositories.PratoCarrinhoRepository;
import io.codekaffee.ifood.marketplace.repositories.PratoRepository;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Traced
@Path("carrinho")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarrinhoResource {

    @Inject
    private PratoCarrinhoRepository pcr;

    @Inject
    private PratoRepository pratoRepository;


    @Inject
    @Channel("pedidos")
    private Emitter<PedidoDTO> emitter;

    private static final String cliente = "a";

    @GET
    public Uni<List<PratoCarrinho>> buscarCarrinho() {
        return pcr.findCarrinho(cliente);
    }


    @POST
    @Path("/prato/{idPrato}")
    public Uni<String> adicionarPrato(@PathParam("idPrato") Long idPrato) {
        PratoCarrinho carrinho = new PratoCarrinho();
        carrinho.setPrato(idPrato);
        carrinho.setUsuario(cliente);

        return pcr.saveCarrinho(carrinho.getUsuario(), carrinho.getPrato());
    }


    @POST
    @Blocking
    @Path("/realizar-pedido")
    public Uni<Boolean> realizarPedido(){
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setCliente(cliente);

        List<PratoCarrinho> carrinho =  pcr.findCarrinho(cliente).await().indefinitely();
        List<PratoPedidoDTO> pratosDTOs =  carrinho.stream().map(this::from).collect(Collectors.toList());

        RestauranteDTO restaurante = new RestauranteDTO();
        restaurante.setNome("Restaurante qualquer");

        pedidoDTO.setPratos(pratosDTOs);
        pedidoDTO.setRestaurante(restaurante);

        emitter.send(pedidoDTO);

        return pcr.delete(cliente);
    }


    private PratoPedidoDTO from(PratoCarrinho pratoCarrinho) {
        PratoDTO dto = pratoRepository.findById(pratoCarrinho.getPrato()).await().indefinitely();
        return new PratoPedidoDTO(dto.getNome(), dto.getDescricao(), dto.getPreco());
    }
}
