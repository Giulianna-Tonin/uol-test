package br.com.cadastro.temperature;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/temperatures")
public class TemperatureController {

    private final TemperatureService service;

    public TemperatureController(TemperatureService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemperatureDTO> getById(@PathVariable("id") Long id) {
        Optional<TemperatureDTO> optionalUser = service.getById(id);
        return optionalUser.map(temperatureDTO -> new ResponseEntity<>(temperatureDTO, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<TemperatureDTO> getAll() {
        return service.getAll();
    }

}
