package com.fatec.starvingless.controllers;

import com.fatec.starvingless.dto.CommentDTO;
import com.fatec.starvingless.entities.Comment;
import com.fatec.starvingless.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/starvingless/comment/v1")
@Tag(name = "Comment", description = "endpoints")
public class CommentController {

    public static final String ID = "/{id}";
    public static final String POST_ID = "/{postId}";
    public static final String USER_ID = "/{userId}";
    @Autowired
    private CommentService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/id" + ID)
    @Operation(summary = "Find a Cooment by Id")
    public ResponseEntity<CommentDTO> findById(@Valid @PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(service.findById(id), CommentDTO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "Find all Comments by page")
    public ResponseEntity<List<CommentDTO>> findAll(@RequestParam(value= "page", defaultValue = "0") int page,
                                                 @RequestParam(value= "size", defaultValue = "10") int size){
        return ResponseEntity.ok().body(service.findAll(PageRequest.of(page, size)).stream()
                .map(obj -> mapper.map(obj, CommentDTO.class)).collect(Collectors.toList()));

    }

    @PostMapping("/create")
    @Operation(summary = "Create a Comment")
    public ResponseEntity<CommentDTO> create(@Valid @RequestBody CommentDTO commentDTO){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("id" + ID)
                .buildAndExpand(service.create(commentDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/update" + ID)
    @Operation(summary = "Update a Comment by Id")
    public ResponseEntity<CommentDTO> updateUser(@PathVariable Long id, @RequestBody @Valid CommentDTO commentDTO) {
        commentDTO.setId(id);
        Comment updatedUser = service.update(commentDTO);
        CommentDTO updatedUserDTO = mapper.map(updatedUser, CommentDTO.class);
        return ResponseEntity.ok(updatedUserDTO);
    }

    @DeleteMapping("/delete" + ID)
    @Operation(summary = "Delete a Comment by Id")
    public ResponseEntity<CommentDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post" + POST_ID)
    @Operation(summary = "Find Comments by Post")
    public ResponseEntity<List<CommentDTO>> postId(
            @PathVariable Long postId,
            @RequestParam(value = "page", defaultValue = "0")Integer page,
            @RequestParam(value = "size", defaultValue = "10")Integer size) {

        return ResponseEntity.ok().body(service.getByPostId(postId, PageRequest.of(page, size)).stream()
                .map(obj -> mapper.map(obj, CommentDTO.class)).collect(Collectors.toList()));

    }

    @GetMapping("/user" + USER_ID)
    @Operation(summary = "Find Comments by Post")
    public ResponseEntity<List<CommentDTO>> userId(
            @PathVariable Long userId,
            @RequestParam(value = "page", defaultValue = "0")Integer page,
            @RequestParam(value = "size", defaultValue = "10")Integer size) {

        return ResponseEntity.ok().body(service.getByUserId(userId, PageRequest.of(page, size)).stream()
                .map(obj -> mapper.map(obj, CommentDTO.class)).collect(Collectors.toList()));

    }
}
