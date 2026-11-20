package com.bank.backend.controller;

import com.bank.backend.dto.request.UserRequest;
import com.bank.backend.dto.response.UserResponse;
import com.bank.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    @Value("${server.port}")
    int port;

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request){
        UserResponse user = userService.createUser(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build().toUri();
        return ResponseEntity.created(location).body(user);
    }

    @GetMapping("/get")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id){
        UserResponse userResponse = userService.getUserById(id);

        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest request, @PathVariable Long id) {
        UserResponse user = userService.updateUser(request, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String user = userService.deleteUser(id);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/test")
    public String testingApi() {
        return "now the port is running on: " + port;
    }
}
