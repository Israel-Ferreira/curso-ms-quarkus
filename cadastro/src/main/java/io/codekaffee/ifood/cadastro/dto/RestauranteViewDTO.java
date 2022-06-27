package io.codekaffee.ifood.cadastro.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.codekaffee.ifood.cadastro.models.Restaurante;

public class RestauranteViewDTO {
    private Long id;
    private String nome;
    private String proprietarioId;


    private String cnpj;


    private LocalizacaoDTO localizacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date dataCriacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date dataAtualizacao;
    
    public RestauranteViewDTO() {
        this.localizacao = new LocalizacaoDTO();
    }

    public RestauranteViewDTO(Restaurante restaurante){
        this.id = restaurante.getId();
        this.nome = restaurante.getNome();
        this.proprietarioId = restaurante.getProprietario();

        this.cnpj = restaurante.getCnpj();

        if(restaurante.getLocalizacao() != null){
            this.localizacao = new LocalizacaoDTO(restaurante.getLocalizacao());
        }

        
        this.dataCriacao = restaurante.getDataCriacao();
        this.dataAtualizacao =  restaurante.getDataAtualizacao();
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

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    

    
}
