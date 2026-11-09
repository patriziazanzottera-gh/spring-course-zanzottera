package com.example.spring_course;

import com.example.spring_course.exception.AppException;
import com.example.spring_course.model.UserResponse;
import com.example.spring_course.repository.UsersRepository;
import com.example.spring_course.repository.model.UserEntity;
import com.example.spring_course.service.UserService;
import com.example.spring_course.service.dto.UserRequestDTO;
import com.example.spring_course.service.dto.UserResponseDTO;
import com.example.spring_course.service.mapper.MapperUserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserApplicationTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private MapperUserDTO mapperUserDTO;

    @Test
    public void calcolaTotale() {
        double prezzo=10.0;
        int quantita = 3;
        double totale = prezzo * quantita;
        assertEquals(30.0, totale);
    }

    @Test
    public void testCreateUser() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("cat75.zan@gmail.it");
        userRequestDTO.setEta(45);
        userRequestDTO.setIndirizzo("Via Genova 56");
        userRequestDTO.setNome("Nome");
        Long oid = new Random().nextLong();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(oid);
        userEntity.setEmail(userRequestDTO.getEmail());
        userEntity.setIndirizzo(userRequestDTO.getIndirizzo());
        userEntity.setEta(userRequestDTO.getEta());
        userEntity.setNome(userRequestDTO.getNome());

        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(usersRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(mapperUserDTO.dtoToEntity(any(UserRequestDTO.class)))
                .thenAnswer(invocation -> {
                    UserRequestDTO dto = invocation.getArgument(0);

                    UserEntity entity = new UserEntity();
                    entity.setEmail(dto.getEmail());
                    entity.setNome(dto.getNome());
                    entity.setIndirizzo(dto.getIndirizzo());
                    entity.setEta(dto.getEta());
                    entity.setId(oid);

                    return entity;
                });
        when(mapperUserDTO.responseToDTO(any(UserResponse.class)))
                .thenAnswer(invocation -> {

                    UserResponse userResponse = invocation.getArgument(0);

                    UserResponseDTO userDTO = new UserResponseDTO();
                    userDTO.setEmail(userResponse.getEmail());
                    userDTO.setIndirizzo(userResponse.getIndirizzo());
                    userDTO.setNome(userResponse.getNome());
                    userDTO.setEta(userResponse.getEta());
                    userDTO.setStatus(userResponse.getStatus());
                    userDTO.setMessage(userResponse.getMessage());

                    return userDTO;
                });
        UserResponseDTO userResponseDTO= userService.createUser(userRequestDTO);
        assertNotNull(userResponseDTO);
        assertEquals(userRequestDTO.getEmail(), userResponseDTO.getEmail());
    }

    @Test
    public void testCreateUserIsAlreadyPresent() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("cat75.zan@gmail.it");
        userRequestDTO.setEta(45);
        userRequestDTO.setIndirizzo("Via Genova 56");
        userRequestDTO.setNome("Nome");
        Long oid = new Random().nextLong();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(oid);
        userEntity.setEmail(userRequestDTO.getEmail());
        userEntity.setIndirizzo(userRequestDTO.getIndirizzo());
        userEntity.setEta(userRequestDTO.getEta());
        userEntity.setNome(userRequestDTO.getNome());

        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));

        AppException appException = assertThrows(AppException.class, ()->{
            userService.createUser(userRequestDTO);
        });

        assertEquals(HttpStatus.CONFLICT, appException.getStatusCode());
    }

    @Test
    public void testFindUserByName() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("cat75.zan@gmail.it");
        userRequestDTO.setEta(45);
        userRequestDTO.setIndirizzo("Via Genova 56");
        userRequestDTO.setNome("Nome");

        Long oid = new Random().nextLong();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(oid);
        userEntity.setEmail(userRequestDTO.getEmail());
        userEntity.setIndirizzo(userRequestDTO.getIndirizzo());
        userEntity.setEta(userRequestDTO.getEta());
        userEntity.setNome(userRequestDTO.getNome());

        when(usersRepository.searchByName(anyString())).thenReturn(List.of(userEntity));

        UserResponseDTO userResponseDTO = userService.getByName(userRequestDTO.getNome());
        assertNotNull(userResponseDTO);
        assertEquals(userRequestDTO.getNome(), userResponseDTO.getNome());
    }

    @Test
    public void testFindUserByNameNotFound() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("cat75.zan@gmail.it");
        userRequestDTO.setEta(45);
        userRequestDTO.setIndirizzo("Via Genova 56");
        userRequestDTO.setNome("Nome");

        Long oid = new Random().nextLong();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(oid);
        userEntity.setEmail(userRequestDTO.getEmail());
        userEntity.setIndirizzo(userRequestDTO.getIndirizzo());
        userEntity.setEta(userRequestDTO.getEta());
        userEntity.setNome(userRequestDTO.getNome());

        when(usersRepository.searchByName(anyString())).thenReturn(Collections.emptyList());

        AppException appException = assertThrows(AppException.class, ()->{
            userService.getByName(userRequestDTO.getNome());
        });
        assertEquals(HttpStatus.NOT_FOUND, appException.getStatusCode());
    }
}