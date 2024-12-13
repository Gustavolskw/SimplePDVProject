package com.web.service.presentation.controller;

import com.web.service.domain.exception.AlreadyExistsException;
import com.web.service.domain.exception.EntityNotFoundException;
import com.web.service.domain.exception.ListEmptyException;
import com.web.service.application.dto.UserRegisterDTO;
import com.web.service.domain.exception.ValidationException;
import com.web.service.presentation.viewModel.ApiResponse;
import com.web.service.presentation.viewModel.UserResponseDTO;
import com.web.service.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping()
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
//        try{
            UserResponseDTO userResponseDTO = userService.createUser(userRegisterDTO);
            return ResponseEntity.ok().body(new ApiResponse("User Created Sucessfully", userResponseDTO));
//        }catch (Exception e){
//            if (e instanceof AlreadyExistsException) return ResponseEntity.status(422).body(new ApiResponse("User Already Exists", null));
//            if (e instanceof ValidationException)return ResponseEntity.status(422).body(new ApiResponse("Validation Error", e.getMessage()));
//            return ResponseEntity.status(400).body(new ApiResponse("User Creation Failed", null));
//        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllUsers() {
        return ResponseEntity.ok().body(new ApiResponse("All Users Sucessfully", userService.findAll()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable("id") Long id) {

        return ResponseEntity.ok().body(new ApiResponse("User Found", userService.findById(id)));


    }



}
