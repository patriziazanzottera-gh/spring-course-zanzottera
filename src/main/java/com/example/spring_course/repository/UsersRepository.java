package com.example.spring_course.repository;

import com.example.spring_course.repository.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {

    public

    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.nome = :name")
    List<UserEntity> searchByName(@Param("name")String name);

    @Query(value = "SELECT * FROM Users WHERE NOME = :name", nativeQuery = true)
    List<UserEntity> searchByNameNative(@Param("name")String name);
}
