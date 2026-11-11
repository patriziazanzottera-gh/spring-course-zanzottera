package com.example.spring_course.client;

import com.example.spring_course.model.ExternalUserResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor

public class ExternalUserClient {

    private final RestClient restClient;

    public ExternalUserResponse findUser(Long id) {
        return  restClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .body(ExternalUserResponse.class);
    }
}
