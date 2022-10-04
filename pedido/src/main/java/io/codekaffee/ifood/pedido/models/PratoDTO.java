package io.codekaffee.ifood.pedido.models;

import java.math.BigDecimal;


public class PratoDTO {
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    

    public PratoDTO() {
    }

    public PratoDTO(Long id, String nome, String descricao, BigDecimal preco) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "PratoDTO [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", preco=" + preco + "]";
    }

    
    
}
