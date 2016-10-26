package com.example.business;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Component
public class UserRepository {
    private static List<User> users = Arrays.asList(
        User.builder().name("Jack").token("jackToken").roles(singletonList("ROLE_Admin")).build(),
        User.builder().name("Mike").token("mikeToken").roles(singletonList("ROLE_User")).build()
    );

    public Optional<User> getUserByToken(String token) {
        return users.stream().filter(user -> user.getToken().equals(token)).findFirst();
    }
}
