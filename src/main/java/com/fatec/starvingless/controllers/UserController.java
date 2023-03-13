package com.fatec.starvingless.controllers;

import com.fatec.starvingless.dto.UserDTO;
import com.fatec.starvingless.repositories.UserRepository;
import com.fatec.starvingless.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/starvingless/v1")
public class UserController {

    public static final String ID = "/{id}";
    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/id" + ID)
    public ResponseEntity<UserDTO> findById(@Valid @PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(service.finById(id), UserDTO.class));
    }
}
