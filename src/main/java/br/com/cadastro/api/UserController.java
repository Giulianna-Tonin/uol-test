package br.com.cadastro.api;

import br.com.cadastro.ipvigilante.api.IpVigilanteDTO;
import br.com.cadastro.ipvigilante.api.IpVigilanteService;
import br.com.cadastro.metaweather.api.MetaWeatherService;
import br.com.cadastro.metaweather.api.TempAverage;
import br.com.cadastro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IpVigilanteService ipVigilanteService;

    @Autowired
    private MetaWeatherService metaWeatherService;

    @PostMapping
    public ResponseEntity<UserDTO> addNewUser(@RequestBody UserDTO user, HttpServletRequest request) {
        IpVigilanteDTO locationByIp = ipVigilanteService.getLocationByIp(request);
        TempAverage tempAverage = metaWeatherService.getTempAverage(locationByIp);
        UserDTO createdUser = userService.create(user, tempAverage);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserbyId(@PathVariable("id") Long id) {
        Optional<UserDTO> optionalUser = userService.getById(id);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
        }
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO newDataUser) {
        if (newDataUser.getName() == null || newDataUser.getAge() == null
                || newDataUser.getPostDayMaxTemp() == null || newDataUser.getPostDayMinTemp() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserDTO userUpdated = userService.modifyUser(id, newDataUser);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}