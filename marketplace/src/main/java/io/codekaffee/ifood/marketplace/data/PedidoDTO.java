package io.codekaffee.ifood.marketplace.data;

import java.util.List;

public class PedidoDTO {
    private List<PratoPedidoDTO> pratos;

    private RestauranteDTO restaurante;

    private String cliente;

    public List<PratoPedidoDTO> getPratos() {
        return pratos;
    }

    public void setPratos(List<PratoPedidoDTO> pratos) {
        this.pratos = pratos;
    }

    public RestauranteDTO getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(RestauranteDTO restaurante) {
        this.restaurante = restaurante;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    
}
