package br.com.cadastro.metaweather.api;

import br.com.cadastro.ipvigilante.api.DataDTO;
import br.com.cadastro.ipvigilante.api.IpVigilanteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class MetaWeatherService {

    @Autowired
    private RestTemplate restTemplate;

    public TempAverage getTempAverage(IpVigilanteDTO locationByIp) {
        WoeidDTO[] woeids = getLocationWoeid(locationByIp);
        TemperatureDTO[] temperatures = getTempByLocation(woeids[0]);
        TempAverage tempAverage = calcTempAverage(temperatures);
        return tempAverage;
    }


    private WoeidDTO[] getLocationWoeid(IpVigilanteDTO locationByIP) {
        DataDTO dataDTOforTemp = locationByIP.getData();
        String cityName = dataDTOforTemp.getCityName();
        ResponseEntity<WoeidDTO[]> woeids = restTemplate.getForEntity("https://www.metaweather.com/api/location/search/?query=" + cityName, WoeidDTO[].class);
        return woeids.getBody();
    }

    private TemperatureDTO[] getTempByLocation(WoeidDTO metaWeatherWoeid) {
        String formattedDate = getFormattedDate();
        ResponseEntity<TemperatureDTO[]> temperatures = restTemplate.getForEntity("https://www.metaweather.com/api/location/" + metaWeatherWoeid.getWoeid() + "/" + formattedDate + "/", TemperatureDTO[].class);
        return temperatures.getBody();
    }

    private TempAverage calcTempAverage(TemperatureDTO[] temperatures) {
        TempAverage tempAverage = new TempAverage();
        tempAverage.setMax(temperatures[0].getMax());
        tempAverage.setMin(temperatures[0].getMin());

        for (int i = 1; i < temperatures.length; i++) {

            TemperatureDTO actual = temperatures[i];
            if (actual.getMax() > tempAverage.getMax()) {
                tempAverage.setMax(actual.getMax());
            }

            if (actual.getMin() < tempAverage.getMin()) {
                tempAverage.setMin(actual.getMin());
            }
        }

        return tempAverage;
    }

    private String getFormattedDate() {
        return new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
    }
}
