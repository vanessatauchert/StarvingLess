package com.fatec.starvingless.controllers;

import com.fatec.starvingless.dto.UserDTO;
import com.fatec.starvingless.dto.UserFireDTO;
import com.fatec.starvingless.entities.User;
import com.fatec.starvingless.entities.UserFire;
import com.fatec.starvingless.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/starvingless/user/v1")
@Tag(name = "User", description = "endpoints")
public class UserController {

    public static final String IDF = "/{id}";
    public static final String ID = IDF;
    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper mapper;


    // IMPLEMENTAÇÃO FIREBASE

//    @GetMapping("/id" + IDF)
//    @Operation(summary = "Find a User by Id")
//    public UserFire findById(@Valid @PathVariable String id) throws ExecutionException, InterruptedException {
//        return service.findById(id);
//    }
//
//    @PostMapping("/create")
//    public UserFire create(@RequestBody UserFireDTO user) throws ExecutionException, InterruptedException {
//        return service.create(user);
//    }


    // ----------------------------------------------------------------

    // IMPLEMENTAÇÃO PADRÃO


    @GetMapping("/id" + ID)
    @Operation(summary = "Find a User by Id")
    public ResponseEntity<UserDTO> findById(@Valid @PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(service.findById(id), UserDTO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "Find all Users by page")
    public ResponseEntity<List<UserDTO>> findAll(@RequestParam(value= "page", defaultValue = "0") int page,
                                                 @RequestParam(value= "size", defaultValue = "10") int size){
        return ResponseEntity.ok().body(service.findAll(PageRequest.of(page, size)).stream()
                .map(obj -> mapper.map(obj, UserDTO.class)).collect(Collectors.toList()));

    }
    @PermitAll
    @PostMapping("/create")
    @Operation(summary = "Create a User")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("id" + ID)
                .buildAndExpand(service.create(userDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @Secured({"ROLE_ADMIN"})
    @PutMapping("/update" + ID)
    @Operation(summary = "Update a User by Id")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO) {
        userDTO.setId(id);
        User updatedUser = service.update(userDTO);
        UserDTO updatedUserDTO = mapper.map(updatedUser, UserDTO.class);
        return ResponseEntity.ok(updatedUserDTO);
    }
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/delete" + ID)
    @Operation(summary = "Delete a User by Id")
    public ResponseEntity<UserDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
