package ru.vlsv.androidone;

import java.io.Serializable;

public class City implements Serializable {
    private String city;
    private Boolean windSpeed;
    private Boolean pressure;

    public City(String city, Boolean windSpeed, Boolean pressure) {
        this.city = city;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
    }

    public City() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Boolean windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Boolean getPressure() {
        return pressure;
    }

    public void setPressure(Boolean pressure) {
        this.pressure = pressure;
    }
}
