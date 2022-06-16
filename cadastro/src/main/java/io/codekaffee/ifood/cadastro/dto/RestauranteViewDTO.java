package io.codekaffee.ifood.cadastro.dto;

import io.codekaffee.ifood.cadastro.models.Restaurante;

public class RestauranteViewDTO {
    private Long id;
    private String nome;
    private String proprietarioId;

    private String cnpj;
    private LocalizacaoDTO localizacao;

    
    public RestauranteViewDTO() {
        this.localizacao = new LocalizacaoDTO();
    }

    public RestauranteViewDTO(Restaurante restaurante){
        this.id = restaurante.getId();
        this.nome = restaurante.getNome();
        this.proprietarioId = restaurante.getProprietario();

        this.cnpj = restaurante.getCnpj();
        this.localizacao = new LocalizacaoDTO(restaurante.getLocalizacao());
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
    public String getProprietarioId() {
        return proprietarioId;
    }
    public void setProprietarioId(String proprietarioId) {
        this.proprietarioId = proprietarioId;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public LocalizacaoDTO getLocalizacao() {
        return localizacao;
    }
    public void setLocalizacao(LocalizacaoDTO localizacao) {
        this.localizacao = localizacao;
    }

    
}
