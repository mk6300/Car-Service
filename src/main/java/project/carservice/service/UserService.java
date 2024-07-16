package project.carservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.carservice.model.dto.RegisterUserDTO;
import project.carservice.model.dto.UserDTO;
import project.carservice.model.entity.User;


public interface UserService {
    UserDTO findUserByUsername(String username);

    UserDTO findUserByEmail(String email) ;

    boolean checkCredentials(String username, String password);

    User getUserByUsername(String username);

    void register(RegisterUserDTO registerDTO);


    UserDetails getCurrentUser();
}
