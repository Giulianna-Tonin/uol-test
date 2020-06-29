package br.com.cadastro.user;


import br.com.cadastro.temperature.TemperatureService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    private final UserRepository repository;
    private final TemperatureService temperatureService;

    public UserService(UserRepository repository, TemperatureService temperatureService) {
        this.repository = repository;
        this.temperatureService = temperatureService;
    }

    public UserDTO save(UserDTO userDTO, HttpServletRequest request) {
        User user = repository.save(toUser(userDTO));
        temperatureService.save(request, user);

        return toDTO(user);
    }

    public Optional<UserDTO> getById(Long id) {
        Optional<User> optionalUser = repository.findById(id);
        return optionalUser.map(this::toDTO);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<UserDTO> getAll() {
        return toDTOList(repository.findAll());
    }

    public UserDTO update(Long id, UserDTO newDataUser) {
        User userToUpdate = toUser(newDataUser);
        userToUpdate.setId(id);
        repository.save(userToUpdate);
        return toDTO(userToUpdate);
    }

    private User toUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setAge(userDTO.getAge());
        return user;
    }

    private UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setAge(user.getAge());
        userDTO.setId(user.getId());
        return userDTO;
    }

    private List<UserDTO> toDTOList(List<User> listUser) {
        List<UserDTO> listUserDTO = new ArrayList<>();

        for (User user : listUser) {
            listUserDTO.add(toDTO(user));
        }
        return listUserDTO;
    }
}


