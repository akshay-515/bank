package com.bank.backend.service;

import com.bank.backend.dto.request.UserRequest;
import com.bank.backend.dto.response.UserResponse;
import com.bank.backend.entity.User;
import com.bank.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserRequest request) {

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("the email :" + request.getEmail() + " already exists!");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();

        User savedUser = userRepository.save(user);

      return mapToResponse(savedUser);

    }

    @Cacheable(value = "users", key = "#id")
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id){
        log.info("Cache MISS - fetching user {} from Postgres", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found with id: " + id));
        return mapToResponse(user);
    }


    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
//        List<User> users =  userRepository.findAll();
//
//        if(users.isEmpty()) {
//            throw new RuntimeException("User not found!");
//        }
//
//        List<UserResponse> response = new ArrayList<>();
//
//        for(User user : users){
//            response.add(mapToResponse(user));
//        }
//
//        return response;

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @CachePut(value = "users", key = "#id")
    @Transactional
    public UserResponse updateUser(UserRequest request, Long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));

        existingUser.setName(request.getName());
        existingUser.setEmail(request.getEmail());

//        User updateUser = User.builder()
//                .name(request.getName())
//                .email(request.getEmail())
//                .build();

        User updatedUser = userRepository.save(existingUser);
        return mapToResponse(updatedUser);
    }

    @Transactional
    @CacheEvict(value = "users", key = "#id")
    public String deleteUser(Long id) {

        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) {
            throw new RuntimeException("user Not found");
        }

        userRepository.deleteById(id);

        return "the user with id: " + id + " has deleted";
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .created_at(user.getCreated_at())
                .build();

        return response;
    }
}
