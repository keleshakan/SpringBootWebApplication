package com.example.MSSQLConnection.webSocket;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SocketController {

    @GetMapping("/serverSocket")
    public String server() throws IOException, ClassNotFoundException {
        new SocketServer();
        return "Server connected!";
    }

    @GetMapping("/clientSocket")
    public String client() throws IOException, ClassNotFoundException, InterruptedException {
        new ClientServer();
        return "Client connected!";
    }
}
