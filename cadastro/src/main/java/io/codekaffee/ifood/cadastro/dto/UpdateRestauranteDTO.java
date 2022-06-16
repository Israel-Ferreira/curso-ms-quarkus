package io.codekaffee.ifood.cadastro.dto;

import javax.validation.constraints.NotBlank;

import io.smallrye.common.constraint.NotNull;

public class UpdateRestauranteDTO {

    @NotNull
    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    
}
