package io.codekaffee.ifood.cadastro.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "restaurante")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String proprietario;

    @CreationTimestamp
    public Date dataCriacao;

    @UpdateTimestamp
    public Date dataAtualizacao;

    public String nome;

    public String cnpj;

    @OneToOne
    public Localizacao localizacao;

    public Restaurante(){
        this.localizacao = new Localizacao();
    }

    public Restaurante(String proprietario, String nome, String cnpj, Double latitude, Double longitude) {
        this.proprietario = proprietario;
        this.nome = nome;
        this.cnpj = cnpj;
        this.localizacao = new Localizacao();

        this.localizacao.latitude = latitude;
        this.localizacao.longitude = longitude;
    }

    


}
