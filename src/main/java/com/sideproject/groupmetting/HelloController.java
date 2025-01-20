package com.sideproject.groupmetting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to the Groupmetting Project!";
    }
}