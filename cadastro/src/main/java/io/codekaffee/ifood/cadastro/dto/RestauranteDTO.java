package io.codekaffee.ifood.cadastro.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CNPJ;

import io.codekaffee.ifood.cadastro.models.Restaurante;
import io.smallrye.common.constraint.NotNull;

public class RestauranteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    @CNPJ(message = "CNPJ Inválido")
    private String cnpj;


    private String proprietarioId;

    private LocalizacaoDTO localizacao;



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

    public LocalizacaoDTO getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(LocalizacaoDTO localizacao) {
        this.localizacao = localizacao;
    }

    public Restaurante toModel() {
        return new Restaurante(
            this.proprietarioId,
            this.nome,
            this.cnpj, 
            this.localizacao.getLatitude(),
            this.localizacao.getLongitude()
        );
    }

}
