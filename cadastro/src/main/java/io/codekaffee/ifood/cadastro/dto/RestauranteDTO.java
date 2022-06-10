package io.codekaffee.ifood.cadastro.dto;

import java.io.Serializable;

import io.codekaffee.ifood.cadastro.models.Restaurante;

public class RestauranteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String cnpj;

    private String proprietarioId;

    private Double latitude;
    private Double longitude;


    public RestauranteDTO() {
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getCnpj() {
        return cnpj;
    }


    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }


    public String getProprietarioId() {
        return proprietarioId;
    }


    public void setProprietarioId(String proprietarioId) {
        this.proprietarioId = proprietarioId;
    }


    public Double getLatitude() {
        return latitude;
    }


    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }


    public Double getLongitude() {
        return longitude;
    }


    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    public Restaurante toModel() {
        return new Restaurante(
            this.proprietarioId,
            this.nome,
            this.cnpj, 
            this.latitude,
            this.longitude
        );
    }

}
