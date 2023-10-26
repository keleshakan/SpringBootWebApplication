package com.example.MSSQLConnection.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        return Collections.singletonMap("message", "Home Page");
    }

    @GetMapping("/home")
    public Map<String, Object> greeting() {
        return Collections.singletonMap("message", "Hello, World");
    }

}
