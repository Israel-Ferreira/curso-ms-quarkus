package io.codekaffee.ifood.pedido.models;

public class RestauranteDTO {
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "RestauranteDTO [nome=" + nome + "]";
    }

    
}