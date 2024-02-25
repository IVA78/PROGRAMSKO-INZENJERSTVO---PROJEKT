package progi.project.eventovci;


import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import progi.project.eventovci.user.controller.dto.ProfileForm;
import progi.project.eventovci.user.entity.User;
import progi.project.eventovci.user.repository.UserRepository;
import progi.project.eventovci.user.service.UserService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class UserTests {

    @BeforeEach
    private void init() {
        System.out.println(userRepository == null);
    }

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User newUser;

    private ProfileForm profileForm;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }


     @Test
     public void loginValidTest() {
        //Arrange
         newUser = new User();
         newUser.setUsername("Ivana");
         newUser.setEmail("ivana@gmail.com");
         newUser.setTypeOfUser("posjetitelj");
         newUser.setHomeAdress("adress");

         //Define mock behavior
         when(userRepository.findUserByUsername(newUser.getUsername())).thenReturn(newUser);

         //Actual test on service
         User savedUser = userService.login(newUser.getUsername());

         //Assertion
         assertNotNull(savedUser);
         assertEquals(newUser, savedUser);
     }

    @Test
    public void loginInvalidTest() {
        //Arrange
        newUser = new User();
        newUser.setUsername("Ivana");

        //Define mock behavior
        lenient().when(userRepository.findUserByUsername(newUser.getUsername())).thenReturn(newUser);

        //Actual test on service
        User savedUser = userService.login("invalidUsername");

        //Assertion
        assertNull(savedUser);
        assertEquals(null,savedUser);
    }

    @Test
    public void retrieveUserDataTest() {
        // Arrange
        newUser = new User();
        newUser.setUsername("Ivana");
        newUser.setEmail("ivana@gmail.com");
        newUser.setTypeOfUser("posjetitelj");
        newUser.setHomeAdress("adress");

        // Define mock behavior
        when(userRepository.findUserByUsername(newUser.getUsername())).thenReturn(newUser);

        // Act
        profileForm = userService.data(newUser.getUsername());

        // Assert
        assertNotNull(profileForm);
        assertEquals(newUser.getUsername(), profileForm.getUsername());
        assertEquals("ivana@gmail.com", profileForm.getEmail());
        assertEquals("posjetitelj", profileForm.getTypeOfUser());
        assertEquals("adress", profileForm.getHomeAdress());
    }

    @Test
    public void changeDataTest() {
        // Arrange
        Long userId = 1L;
        String newUsername = "newUsername";
        String newEmail = "newEmail@example.com";
        String newHomeAddress = "newAddress";

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("oldUsername");
        existingUser.setEmail("oldEmail@example.com");
        existingUser.setHomeAdress("oldAddress");

        // Mock repository behavior
        when(userRepository.findUserById(userId)).thenReturn(existingUser);

        // Mock repository behavior for data update
        doAnswer(invocation -> {
            Long idArg = invocation.getArgument(0);
            String usernameArg = invocation.getArgument(1);
            String emailArg = invocation.getArgument(2);
            String homeAddressArg = invocation.getArgument(3);

            assertEquals(userId, idArg);
            assertEquals(newUsername, usernameArg);
            assertEquals(newEmail, emailArg);
            assertEquals(newHomeAddress, homeAddressArg);

            existingUser.setUsername(newUsername);
            existingUser.setEmail(newEmail);
            existingUser.setHomeAdress(newHomeAddress);

            return null;
        }).when(userRepository).updateUserById(anyLong(), anyString(), anyString(), anyString());


        // Act
        userService.changeData(userId, newUsername, newEmail, newHomeAddress);
        when(userRepository.findUserByUsername(existingUser.getUsername())).thenReturn(existingUser);
        User changedUser = userService.login(existingUser.getUsername());

        // Assert
        verify(userRepository, times(1)).updateUserById(userId, newUsername, newEmail, newHomeAddress);
        assertNotNull(changedUser);
    }

    @Test
    public void deleteMyProfileTest() {
        // Arrange
        Long userId = 1L;
        newUser = new User();
        newUser.setUsername("Ivana");
        newUser.setId(userId);

        // Mock repository behavior
        when(userRepository.findUserById(userId)).thenReturn(newUser);

        // Act
        userService.deleteMyProfile(userId);
        User deletedUser = userService.login(newUser.getUsername());

        // Assert
        verify(userRepository, times(1)).deleteUserById(userId);
        assertNull(deletedUser);
        assertEquals(null,deletedUser);

    }
}
