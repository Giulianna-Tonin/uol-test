package br.com.cadastro.temperature.metaweather;

import br.com.cadastro.temperature.Temperature;
import br.com.cadastro.temperature.ipvigilante.IpVigilanteDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class MetaWeatherService {

    private final RestTemplate restTemplate;

    public MetaWeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherDTO getWeather(IpVigilanteDTO location) {
        WoeidDTO[] woeids = getLocationWoeids(location);
        WeatherDTO[] weathers = getWeathersByLocation(woeids[0].getWoeid());

        return getWeatherRange(weathers);
    }

    private WoeidDTO[] getLocationWoeids(IpVigilanteDTO locationByIP) {
        String cityName = locationByIP.getData().getCityName();
        return restTemplate.getForObject("https://www.metaweather.com/api/location/search/?query=" + cityName, WoeidDTO[].class);
    }

    private WeatherDTO[] getWeathersByLocation(Integer woeid) {
        String formattedDate = getFormattedDate();
        return restTemplate.getForObject("https://www.metaweather.com/api/location/" + woeid + "/" + formattedDate + "/", WeatherDTO[].class);
    }

    private WeatherDTO getWeatherRange(WeatherDTO[] weathers) {
        WeatherDTO weatherDTO = new WeatherDTO();

        List<Double> maxTemps = new ArrayList<>();
        List<Double> minTemps = new ArrayList<>();

        for(WeatherDTO weather : weathers) {
            maxTemps.add(weather.getMax());
            minTemps.add(weather.getMin());
        }

        Optional<Double> max = maxTemps.stream().max(Double::compareTo);
        Optional<Double> min = minTemps.stream().min(Double::compareTo);

        max.ifPresent(weatherDTO::setMax);
        min.ifPresent(weatherDTO::setMin);

        return weatherDTO;
    }

    private String getFormattedDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

}
