package io.codekaffee.ifood.pedido.models;

public class Localizacao {
    private Double latitude;

    private Double longitude;

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

    @Override
    public String toString() {
        return "Localizacao [latitude=" + latitude + ", longitude=" + longitude + "]";
    }
    

}
