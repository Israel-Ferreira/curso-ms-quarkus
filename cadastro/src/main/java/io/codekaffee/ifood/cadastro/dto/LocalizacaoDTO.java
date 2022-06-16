package io.codekaffee.ifood.cadastro.dto;

import io.codekaffee.ifood.cadastro.models.Localizacao;

public class LocalizacaoDTO {
    private Double latitude;
    private Double longitude;

    public LocalizacaoDTO() {
    }

    public LocalizacaoDTO(Localizacao localizacao){
        this.latitude = localizacao.getLatitude();
        this.longitude = localizacao.getLongitude();
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
}
