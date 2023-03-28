package com.fatec.starvingless.controllers;

import com.fatec.starvingless.dto.CommentDTO;
import com.fatec.starvingless.dto.PostDTO;
import com.fatec.starvingless.dto.UserDTO;
import com.fatec.starvingless.entities.Post;
import com.fatec.starvingless.services.PostService;
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

import static com.fatec.starvingless.controllers.CommentController.USER_ID;

@RestController
@RequestMapping("/api/starvingless/post/v1")
@Tag(name = "Posts", description = "endpoints")
public class PostController {

    public static final String ID = "/{id}";
    @Autowired
    private PostService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/id" + ID)
    @Operation(summary = "Find a Post by Id")
    public ResponseEntity<PostDTO> findById(@Valid @PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(service.findById(id), PostDTO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "Find all Posts by page")
    public ResponseEntity<List<PostDTO>> findAll(@RequestParam(value= "page", defaultValue = "0") int page,
                                                 @RequestParam(value= "size", defaultValue = "10") int size){
        return ResponseEntity.ok().body(service.findAll(PageRequest.of(page, size)).stream()
                .map(obj -> mapper.map(obj, PostDTO.class)).collect(Collectors.toList()));

    }

    @PostMapping("/create")
    @Operation(summary = "Create a Post")
    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostDTO postDTO){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("id" + ID)
                .buildAndExpand(service.create(postDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/update" + ID)
    @Operation(summary = "Update a Post by Id")
    public ResponseEntity<PostDTO> updateUser(@PathVariable Long id, @RequestBody @Valid PostDTO postDTO) {
        postDTO.setId(id);
        Post updatedPost = service.update(postDTO);
        PostDTO updatedPostDTO = mapper.map(updatedPost, PostDTO.class);
        return ResponseEntity.ok(updatedPostDTO);
    }

    @DeleteMapping("/delete" + ID)
    @Operation(summary = "Delete a Post by Id")
    public ResponseEntity<PostDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user" + USER_ID)
    @Operation(summary = "Find Comments by Post")
    public ResponseEntity<List<PostDTO>> userId(
            @PathVariable Long userId,
            @RequestParam(value = "page", defaultValue = "0")Integer page,
            @RequestParam(value = "size", defaultValue = "10")Integer size) {

        return ResponseEntity.ok().body(service.getByUserId(userId, PageRequest.of(page, size)).stream()
                .map(obj -> mapper.map(obj, PostDTO.class)).collect(Collectors.toList()));

    }
}
