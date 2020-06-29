package br.com.cadastro.temperature;

import br.com.cadastro.temperature.ipvigilante.IpVigilanteDTO;
import br.com.cadastro.temperature.ipvigilante.IpVigilanteService;
import br.com.cadastro.temperature.metaweather.MetaWeatherService;
import br.com.cadastro.temperature.metaweather.WeatherDTO;
import br.com.cadastro.user.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TemperatureService {

    private final TemperatureRepository repository;
    private final IpVigilanteService ipVigilanteService;
    private final MetaWeatherService metaWeatherService;

    public TemperatureService(TemperatureRepository repository, IpVigilanteService ipVigilanteService, MetaWeatherService metaWeatherService) {
        this.repository = repository;
        this.ipVigilanteService = ipVigilanteService;
        this.metaWeatherService = metaWeatherService;
    }

    public void save(HttpServletRequest request, User newUser) {
        IpVigilanteDTO location = ipVigilanteService.findLocationByIp(request);
        WeatherDTO weather = metaWeatherService.getWeather(location);

        Temperature newTemperature = new Temperature();

        newTemperature.setUserId(newUser.getId());
        newTemperature.setMaxTemp(weather.getMax());
        newTemperature.setMinTemp(weather.getMin());
        newTemperature.setLocation(location.getData().getCityName());
        newTemperature.setDate(LocalDate.now());

        repository.save(newTemperature);
    }

    public Optional<TemperatureDTO> getById(Long id) {
        Optional<Temperature> optionalTemperature = repository.findById(id);
        return optionalTemperature.map(this::toDTO);
    }

    public List<TemperatureDTO> getAll() {
        return toDTOList(repository.findAll());
    }


    private TemperatureDTO toDTO(Temperature temperature) {
        TemperatureDTO temperatureDTO = new TemperatureDTO();

        temperatureDTO.setId(temperature.getId());
        temperatureDTO.setUserId(temperature.getUserId());
        temperatureDTO.setMaxTemp(temperature.getMaxTemp());
        temperatureDTO.setMinTemp(temperature.getMinTemp());
        temperatureDTO.setLocation(temperature.getLocation());
        temperatureDTO.setDate(temperature.getDate());

        return temperatureDTO;
    }

    private List<TemperatureDTO> toDTOList(List<Temperature> listTemperature) {
        List<TemperatureDTO> listTemperatureDTO = new ArrayList<>();

        for (Temperature temperature : listTemperature) {
            listTemperatureDTO.add(toDTO(temperature));
        }
        return listTemperatureDTO;
    }

}
