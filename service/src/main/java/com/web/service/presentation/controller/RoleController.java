package com.web.service.presentation.controller;

import com.web.service.application.service.RoleService;
import com.web.service.domain.exception.AlreadyExistsException;
import com.web.service.domain.exception.EntityNotFoundException;
import com.web.service.domain.exception.ListEmptyException;
import com.web.service.presentation.viewModel.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/role")
@Slf4j
@RequiredArgsConstructor
public class RoleController {
        private final RoleService roleService;

    @GetMapping
    public ResponseEntity<ApiResponse> GetAllRoles() {
        try{
            return ResponseEntity.ok().body(new ApiResponse("Success",roleService.findAll()));
        } catch (Exception e) {
            if(e instanceof ListEmptyException)return ResponseEntity.ok().body(new ApiResponse(e.getMessage(), null));
            log.error(e.getMessage());
            return ResponseEntity.status(400).body(new ApiResponse("Error ao buscar Lista de Acesos", null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> GetRoleById(@PathVariable("id") Long id) {
        try{
            return ResponseEntity.ok().body(new ApiResponse("Success",roleService.findById(id)));
        }catch (Exception e) {
            if(e instanceof EntityNotFoundException) return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
            log.error(e.getMessage());
            return ResponseEntity.status(400).body(new ApiResponse("Erro ao Buscar usuario", null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> CreateRole(@RequestBody String role) {
        try{
            return ResponseEntity.ok().body(new ApiResponse("Success",roleService.create(role)));
        }catch (Exception e) {
            if(e instanceof AlreadyExistsException) return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse("Erro ao Criar usuario", null));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> UpdateRole(@PathVariable("id") Long id, @RequestBody String role) {
        try{
            return ResponseEntity.ok().body(new ApiResponse("Success",roleService.update(id, role)));
        }catch (Exception e) {
            if(e instanceof EntityNotFoundException) return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
            if(e instanceof AlreadyExistsException) return ResponseEntity.status(422).body(new ApiResponse(e.getMessage(), null));
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse("Erro ao Atualizar usuario", null));
        }
    }
}
