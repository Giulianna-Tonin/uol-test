package br.com.cadastro.temperature.ipvigilante;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataDTO {

    @JsonProperty("city_name")
    private String cityName;

    public String getCityName() {
        return cityName;
    }

}
