package br.com.cadastro.service;

import br.com.cadastro.api.UserDTO;
import br.com.cadastro.metaweather.api.TempAverage;
import br.com.cadastro.repository.UserModel;
import br.com.cadastro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO create(UserDTO user, TempAverage tempAverage) {
        UserModel userModel = userDTOToUserModel(addTempMaxAndMin(user, tempAverage));
        UserDTO userDTO = userModelToUserDTO(userRepository.save(userModel));
        return userDTO;
    }

    public Optional<UserDTO> getById(Long id) {
        Optional<UserModel> optionalUserModel = userRepository.findById(id);
        if (!optionalUserModel.isPresent()) {
            return Optional.empty();
        }
        UserModel userModel = optionalUserModel.get();
        UserDTO userDTO = userModelToUserDTO(userModel);
        return Optional.of(userDTO);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserDTO> getAllUsers() {
        List<UserModel> listModel = userRepository.findAll();
        List<UserDTO> listDTO = listModeltoListDTO(listModel);
        return listDTO;
    }

    public UserDTO modifyUser(Long id, UserDTO newDataUser) {
        UserModel userModelToModify = userDTOToUserModel(newDataUser);
        userModelToModify.setId(id);
        userRepository.save(userModelToModify);
        return userModelToUserDTO(userModelToModify);
    }

    private UserModel userDTOToUserModel(UserDTO userDTO) {
        UserModel userModel = new UserModel();
        userModel.setName(userDTO.getName());
        userModel.setAge(userDTO.getAge());
        userModel.setPostDayMaxTemp(userDTO.getPostDayMaxTemp());
        userModel.setPostDayMinTemp(userDTO.getPostDayMinTemp());
        return userModel;
    }

    private UserDTO userModelToUserDTO(UserModel userModel) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(userModel.getName());
        userDTO.setAge(userModel.getAge());
        userDTO.setId(userModel.getId());
        userDTO.setPostDayMaxTemp(userModel.getPostDayMaxTemp());
        userDTO.setPostDayMinTemp(userModel.getPostDayMinTemp());
        return userDTO;
    }

    private List<UserDTO> listModeltoListDTO(List<UserModel> listModel) {
        List<UserDTO> listDTO = new ArrayList<>();
        for (int i = 0; i < listModel.size(); i++) {
            UserModel userModel = listModel.get(i);
            listDTO.add(userModelToUserDTO(userModel));
        }
        return listDTO;
    }

    private UserDTO addTempMaxAndMin(UserDTO user, TempAverage tempAverage) {
        user.setPostDayMaxTemp(tempAverage.getMax());
        user.setPostDayMinTemp(tempAverage.getMin());
        return user;
    }
}


