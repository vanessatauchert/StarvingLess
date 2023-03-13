package com.fatec.starvingless.controllers;

import com.fatec.starvingless.dto.UserDTO;
import com.fatec.starvingless.repositories.UserRepository;
import com.fatec.starvingless.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> findAll(@RequestParam(value= "page", defaultValue = "0") int page,
                                                 @RequestParam(value= "size", defaultValue = "10") int size){
        return ResponseEntity.ok().body(service.findAll(PageRequest.of(page, size)).stream()
                .map(obj -> mapper.map(obj, UserDTO.class)).collect(Collectors.toList()));

    }
}
