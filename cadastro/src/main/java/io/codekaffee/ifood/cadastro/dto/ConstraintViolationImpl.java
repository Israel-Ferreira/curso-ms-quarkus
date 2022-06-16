package io.codekaffee.ifood.cadastro.dto;

import java.io.Serializable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class ConstraintViolationImpl implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Schema(description = "Path do Atributo", required = false)
    private String atributo;

    @Schema(description = "Mensagem de erro do atributo e do path", required = true)
    private String mensagem;


    public ConstraintViolationImpl(ConstraintViolation<?> violation){
        this.mensagem = violation.getMessage();
        this.atributo = Stream.of(violation.getPropertyPath().toString().split("\\.")).skip(2).collect(Collectors.joining("."));
    }



    public ConstraintViolationImpl(String atributo, String mensagem) {
        this.atributo = atributo;
        this.mensagem = mensagem;
    }


    public static ConstraintViolationImpl of(ConstraintViolation<?> violation) {
        return new ConstraintViolationImpl(violation);
    }

    public static ConstraintViolationImpl of(String violation) {
        return new ConstraintViolationImpl(null, violation);
    }



    public String getAtributo() {
        return atributo;
    }
    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }
    public String getMensagem() {
        return mensagem;
    }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    
    
}
