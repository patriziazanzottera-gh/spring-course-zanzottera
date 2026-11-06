package com.example.spring_course;

import com.example.spring_course.model.UserResponse;
import com.example.spring_course.repository.UsersRepository;
import com.example.spring_course.repository.model.UserEntity;
import com.example.spring_course.service.UserService;
import com.example.spring_course.service.dto.UserRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

    @Test
    public void createUserTest() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("pat.zan@gmail.it");
        userRequestDTO.setNome("Nome");
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));

        UserResponse userResponse = userService.createUser(userRequestDTO);
        assertNotNull(userResponse);
        assertEquals(1L, userResponse.getUserId());
        //TODO fai un altro metodo
        assertThrows(Exception.class, ()->{
           userService.createUser(null);
        });
    }
}
