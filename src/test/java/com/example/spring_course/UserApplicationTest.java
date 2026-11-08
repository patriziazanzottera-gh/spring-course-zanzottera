package com.example.spring_course;

import com.example.spring_course.exception.AppException;
import com.example.spring_course.repository.UsersRepository;
import com.example.spring_course.repository.model.UserEntity;
import com.example.spring_course.service.UserService;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.dto.UserResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserApplicationTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UsersRepository usersRepository;

    @Test
    public void calcolaTotale() {
        double prezzo=10.0;
        int quantita = 3;
        double totale = prezzo * quantita;
        assertEquals(30.0, totale);
    }

    /*@Test
    public void createUserTest() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("pat75.zan@gmail.it");
        userRequestDTO.setNome("Nome");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(188L);

        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));

        ResponseEntity<UserResponse> userResponse = userService.createUser(userRequestDTO);
        assertNotNull(userResponse);
      //  assertEquals(1L, userResponse.getBody().getUserId());
        //TODO fai un altro metodo
        //assertThrows(Exception.class, ()->{
         //  userService.createUser(userRequestDTO);
        //});
    }*/

    /*void shouldCreateUserWhenEmailDoesNotExist() {
        UserRequestDTO request = new UserRequestDTO();
        request.setUsername("cross");
        request.setEmail("mario.rossi@example.com");
        request.setFirstName("Mario");
        request.setLastName("Rossi");
        request.setAddress("Via Roma 1");

        UserEntity savedUser = new UserEntity();
        savedUser.setUsername("cross");
        savedUser.setEmail("mario.rossi@example.com");
        savedUser.setFirstName("Mario");
        savedUser.setLastName("Rossi");
        savedUser.setAddress("Via Roma 1");

        when(usersRepository.findByEmail("mario.rossi@example.com"))
                .thenReturn(Optional.empty());

        when(usersRepository.save(any(UserEntity.class)))
                .thenReturn(savedUser);

        UserResponseDTO response = userService.createUser(request);

        assertNotNull(response);
        assertEquals("mario.rossi@example.com", response.getEmail());
        assertEquals("Mario", response.getFirstName());
        assertEquals("Rossi", response.getLastName());
        assertNotNull(response.getRequestId());

        verify(usersRepository).findByEmail("mario.rossi@example.com");
        verify(usersRepository).save(any(UserEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingUser() {
        String email = "mario.rossi@example.com";

        when(usersRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        AppException exception = assertThrows(
                AppException.class,
                () -> userService.updateUser(email, new UserRequestDTO())
        );

        assertEquals("User not found", exception.getMessage());
    }*/
}