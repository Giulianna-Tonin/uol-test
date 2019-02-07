package br.com.cadastro.ipvigilante.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IpVigilanteDTO {

    private String status;
    private DataDTO data;

    public DataDTO getData() {
        return data;
    }

}
