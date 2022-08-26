package io.codekaffee.ifood.pedido.models;

import java.util.List;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "pedidos", database = "pedido")
public class Pedido extends PanacheMongoEntity {
    
    private String cliente;

    private List<Prato> pratos;

    private String entregador;

    private Restaurante restaurante;


    private Localizacao localizacaoEntregador;


    public Localizacao getLocalizacaoEntregador() {
        return localizacaoEntregador;
    }

    public void setLocalizacaoEntregador(Localizacao localizacaoEntregador) {
        this.localizacaoEntregador = localizacaoEntregador;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<Prato> getPratos() {
        return pratos;
    }

    public void setPratos(List<Prato> pratos) {
        this.pratos = pratos;
    }

    public String getEntregador() {
        return entregador;
    }

    public void setEntregador(String entregador) {
        this.entregador = entregador;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }


    
}
