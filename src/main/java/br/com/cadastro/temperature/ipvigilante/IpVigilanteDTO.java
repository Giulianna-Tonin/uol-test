package br.com.cadastro.temperature.ipvigilante;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IpVigilanteDTO {

    private DataDTO data;

    public DataDTO getData() {
        return data;
    }

}
