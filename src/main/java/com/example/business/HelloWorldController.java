package com.example.business;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endpoint")
public class HelloWorldController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "HelloWorld";
    }

    @GetMapping("/hello-secured")
    @Secured("ROLE_Admin")
    public String helloWorldS() {
        return "HelloWorld secured";
    }

    @PostMapping("/hello-secured")
    @Secured("ROLE_Admin")
    public String helloWorldSPost() {
        return "HelloWorld secured post";
    }
}
