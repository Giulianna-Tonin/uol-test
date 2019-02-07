package br.com.cadastro.ipvigilante.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataDTO {

    @JsonProperty("city_name")
    private String cityName;

    public String getCityName() {
        return cityName;
    }

}
