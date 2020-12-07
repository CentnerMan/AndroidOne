package ru.vlsv.androidone.entities;

public class HistoryData {
    private String city;
    private String temperature;
    private String humidity;
    private String pressure;
    private String windSpeed;

    public HistoryData(String city, String temperature, String humidity, String pressure, String windSpeed) {
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
    }

    public String getCity() {
        return city;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getWindSpeed() {
        return windSpeed;
    }
}
