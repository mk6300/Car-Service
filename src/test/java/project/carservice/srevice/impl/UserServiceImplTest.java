package project.carservice.srevice.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.carservice.model.dto.addDTO.RegisterUserDTO;
import project.carservice.model.dto.UserDTO;
import project.carservice.model.dto.editDTO.EditUserDTO;
import project.carservice.model.entity.User;
import project.carservice.model.entity.UserRole;
import project.carservice.model.entity.enums.UserRoleEnum;
import project.carservice.repository.UserRepository;
import project.carservice.repository.UserRoleRepository;
import project.carservice.service.MailService;
import project.carservice.service.impl.UserServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private MailService mailService;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;
    private RegisterUserDTO registerUserDTO;
    private EditUserDTO editUserDTO;
    private UserRole userRole;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        user = new User();
        user.setId(userId);
        user.setUsername("testUser");
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");

        userRole = new UserRole();
        userRole.setRole(UserRoleEnum.USER);

        userDTO = new UserDTO();
        userDTO.setUsername("testUser");

        registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setUsername("newUser");
        registerUserDTO.setPassword("newPassword");

        editUserDTO = new EditUserDTO();
        editUserDTO.setId(userId);
        editUserDTO.setFirstName("Jane");
        editUserDTO.setLastName("Smith");
        editUserDTO.setEmail("jane.smith@example.com");
    }

    @Test
    void findUserByUserName_ShouldReturnUserDTO() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO foundUser = userService.findUserByUsername("testUser");

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals("testUser", foundUser.getUsername());
    }

    @Test
    void findUserByEmail_ShouldReturnUserDTO() {
        when(userRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO foundUser = userService.findUserByEmail("testuser@example.com");

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals("testUser", foundUser.getUsername());
    }

    @Test
    void getUserById_ShouldReturnUserDTO() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO foundUser = userService.getUserById(userId);

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals("testUser", foundUser.getUsername());
    }

    @Test
    void getUserEditById_ShouldReturnEditUserDTO() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, EditUserDTO.class)).thenReturn(editUserDTO);

        EditUserDTO foundUser = userService.getUserEditById(userId);

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals("Jane", foundUser.getFirstName());
    }

    @Test
    void register_ShouldSaveUser() {
        when(modelMapper.map(registerUserDTO, User.class)).thenReturn(user);
        when(userRoleRepository.findByRole(UserRoleEnum.USER)).thenReturn(Optional.of(userRole));
        when(encoder.encode("newPassword")).thenReturn("encodedPassword");

        userService.register(registerUserDTO);

        verify(userRepository, times(1)).save(user);
        Assertions.assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void makeMechanic_ShouldAddMechanicRole() {
        UserRole mechanicRole = new UserRole();
        mechanicRole.setRole(UserRoleEnum.MECHANIC);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRoleRepository.findByRole(UserRoleEnum.MECHANIC)).thenReturn(Optional.of(mechanicRole));

        userService.makeMechanic(userId);

        verify(userRepository, times(1)).save(user);
        Assertions.assertTrue(user.getRoles().contains(mechanicRole));
    }

    @Test
    void removeUser_ShouldDeleteUser() {
        userService.removeUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void removeMechanic_ShouldRemoveMechanicRole() {
        UserRole mechanicRole = new UserRole();
        mechanicRole.setRole(UserRoleEnum.MECHANIC);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRoleRepository.findByRole(UserRoleEnum.MECHANIC)).thenReturn(Optional.of(mechanicRole));

        userService.removeMechanic(userId);

        verify(userRepository, times(1)).save(user);
        Assertions.assertFalse(user.getRoles().contains(mechanicRole));
    }

    @Test
    void makeAdmin_ShouldAddAdminRole() {
        UserRole adminRole = new UserRole();
        adminRole.setRole(UserRoleEnum.ADMIN);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRoleRepository.findByRole(UserRoleEnum.ADMIN)).thenReturn(Optional.of(adminRole));

        userService.makeAdmin(userId);

        verify(userRepository, times(1)).save(user);
        Assertions.assertTrue(user.getRoles().contains(adminRole));
    }

    @Test
    void removeAdmin_ShouldRemoveAdminRole() {
        UserRole adminRole = new UserRole();
        adminRole.setRole(UserRoleEnum.ADMIN);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRoleRepository.findByRole(UserRoleEnum.ADMIN)).thenReturn(Optional.of(adminRole));

        userService.removeAdmin(userId);

        verify(userRepository, times(1)).save(user);
        Assertions.assertFalse(user.getRoles().contains(adminRole));
    }
}
