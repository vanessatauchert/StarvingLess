package com.fatec.starvingless.controllers;

import com.fatec.starvingless.dto.UserDTO;
import com.fatec.starvingless.entities.User;
import com.fatec.starvingless.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/starvingless/v1")
public class UserController {

    public static final String ID = "/{id}";
    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/id" + ID)
    public ResponseEntity<UserDTO> findById(@Valid @PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(service.findById(id), UserDTO.class));
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> findAll(@RequestParam(value= "page", defaultValue = "0") int page,
                                                 @RequestParam(value= "size", defaultValue = "10") int size){
        return ResponseEntity.ok().body(service.findAll(PageRequest.of(page, size)).stream()
                .map(obj -> mapper.map(obj, UserDTO.class)).collect(Collectors.toList()));

    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("id" + ID)
                .buildAndExpand(service.create(userDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/update" + ID)
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        userDTO.setId(id);
        User updatedUser = service.update(userDTO);
        UserDTO updatedUserDTO = mapper.map(updatedUser, UserDTO.class);
        return ResponseEntity.ok(updatedUserDTO);
    }

    @DeleteMapping("/delete" + ID)
    public ResponseEntity<UserDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
