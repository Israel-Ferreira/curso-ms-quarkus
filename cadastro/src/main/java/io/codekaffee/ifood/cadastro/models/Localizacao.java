package io.codekaffee.ifood.cadastro.models;

import javax.persistence.*;

@Entity
@Table(name = "localizacao")
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Double latitude;

    public  Double longitude;
}
