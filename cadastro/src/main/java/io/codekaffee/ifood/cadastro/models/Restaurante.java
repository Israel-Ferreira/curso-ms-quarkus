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
    private Long id;

    private String proprietario;

    @CreationTimestamp
    private Date dataCriacao;

    @UpdateTimestamp
    private Date dataAtualizacao;

    private String nome;

    private String cnpj;

    @OneToOne
    private Localizacao localizacao;

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

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
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

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
        result = prime * result + ((dataAtualizacao == null) ? 0 : dataAtualizacao.hashCode());
        result = prime * result + ((dataCriacao == null) ? 0 : dataCriacao.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((localizacao == null) ? 0 : localizacao.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((proprietario == null) ? 0 : proprietario.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Restaurante other = (Restaurante) obj;
        if (cnpj == null) {
            if (other.cnpj != null)
                return false;
        } else if (!cnpj.equals(other.cnpj))
            return false;
        if (dataAtualizacao == null) {
            if (other.dataAtualizacao != null)
                return false;
        } else if (!dataAtualizacao.equals(other.dataAtualizacao))
            return false;
        if (dataCriacao == null) {
            if (other.dataCriacao != null)
                return false;
        } else if (!dataCriacao.equals(other.dataCriacao))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (localizacao == null) {
            if (other.localizacao != null)
                return false;
        } else if (!localizacao.equals(other.localizacao))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (proprietario == null) {
            if (other.proprietario != null)
                return false;
        } else if (!proprietario.equals(other.proprietario))
            return false;
        return true;
    }

    
    

}
