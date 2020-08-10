package ru.vlsv.androidone.entities;

import java.io.Serializable;

public class City implements Serializable {
    private String city;
    private Double temp;
    private Double windSpeed;
    private Double pressure;
    private Boolean isWindSpeed;
    private Boolean isPressure;

    public City(String city, Boolean isWindSpeed, Boolean isPressure) {
        this.city = city;
        this.isWindSpeed = isWindSpeed;
        this.isPressure = isPressure;
    }

    public City(String city, Double temp) {
        this.city = city;
        this.temp = temp;
    }

    public City(String city) {
        this.city = city;
    }

    public City() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getIsWindSpeed() {
        return isWindSpeed;
    }

    public void setIsWindSpeed(Boolean isWindSpeed) {
        this.isWindSpeed = isWindSpeed;
    }

    public Boolean getIsPressure() {
        return isPressure;
    }

    public void setIsPressure(Boolean isPressure) {
        this.isPressure = isPressure;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }
}
