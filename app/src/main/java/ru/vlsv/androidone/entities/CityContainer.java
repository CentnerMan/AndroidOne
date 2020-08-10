package ru.vlsv.androidone.entities;

import java.io.Serializable;

public class CityContainer implements Serializable {
    private int position = 0;
    private City city;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public CityContainer() {
    }

    public CityContainer(City city) {
        this.city = city;
    }

    public CityContainer(int position, City city) {
        this.position = position;
        this.city = city;
    }
}
