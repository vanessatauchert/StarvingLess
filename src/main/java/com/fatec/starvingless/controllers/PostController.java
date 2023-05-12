package com.fatec.starvingless.controllers;


import com.fatec.starvingless.dto.PostDTO;
import com.fatec.starvingless.dto.UserDTO;
import com.fatec.starvingless.entities.Post;
import com.fatec.starvingless.entities.User;
import com.fatec.starvingless.services.PostService;
import com.fatec.starvingless.services.UserService;
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
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/pt/id" + ID)
    @Operation(summary = "Find a Post by Id")
    public ResponseEntity<PostDTO> findById(@Valid @PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(service.findById(id), PostDTO.class));
    }

//    @GetMapping("/pt/list")
//    @Operation(summary = "Find all Posts by page")
//    public ResponseEntity<List<PostDTO>> findAll(@RequestParam(value= "page", defaultValue = "0") int page,
//                                                 @RequestParam(value= "size", defaultValue = "10") int size){
//        return ResponseEntity.ok().body(service.findAll(PageRequest.of(page, size)).stream()
//                .map(obj -> mapper.map(obj, PostDTO.class)).collect(Collectors.toList()));
//
//    }

    @GetMapping("/pt/list")
    @Operation(summary = "Find all Posts by page")
    public ResponseEntity<List<PostDTO>> findAll(@RequestParam(value= "page", defaultValue = "0") int page,
                                                 @RequestParam(value= "size", defaultValue = "10") int size){
        return ResponseEntity.ok().body(service.findAll(PageRequest.of(page, size)).stream()
                .map(obj -> mapper.map(obj, PostDTO.class)).collect(Collectors.toList()));

    }

//    @PostMapping("/pt/create")
//    @Operation(summary = "Create a Post")
//    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostDTO postDTO){
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("id" + ID)
//                .buildAndExpand(service.create(postDTO).getId()).toUri();
//        return ResponseEntity.created(uri).build();
//    }

    @PostMapping("/pt/create")
    @Operation(summary = "Create a Post")
    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostDTO postDTO){
        User user = userService.findById(postDTO.getUserId());
        String firstName = user.getFirstName();

        PostDTO post = new PostDTO();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setCreateDate(postDTO.getCreateDate());
        post.setThreadOpen(postDTO.isThreadOpen());

        // Adiciona o ID e o nome do usuário ao objeto Post
        post.setUserId(postDTO.getUserId());
        post.setFirstName(firstName);

        // Salva o objeto Post no banco de dados
        Post savedPost = service.create(post);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();

        PostDTO savedPostDTO = new PostDTO(savedPost, firstName);
        savedPostDTO.setFirstName(firstName);

        return ResponseEntity.created(uri).body(new PostDTO(savedPost, firstName));
    }

    @PutMapping("/pt/update" + ID)
    @Operation(summary = "Update a Post by Id")
    public ResponseEntity<PostDTO> updateUser(@PathVariable Long id, @RequestBody @Valid PostDTO postDTO) {
        postDTO.setId(id);

        User user = userService.findById(postDTO.getUserId());
        String firstName = user.getFirstName();

        Post updatedPost = service.update(postDTO);
        PostDTO updatedPostDTO = mapper.map(updatedPost, PostDTO.class);
        return ResponseEntity.ok(updatedPostDTO);
    }

    @DeleteMapping("/pt/delete" + ID)
    @Operation(summary = "Delete a Post by Id")
    public ResponseEntity<PostDTO> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pt/user" + USER_ID)
    @Operation(summary = "Find Posts by User")
    public ResponseEntity<List<PostDTO>> userId(
            @PathVariable Long userId,
            @RequestParam(value = "page", defaultValue = "0")Integer page,
            @RequestParam(value = "size", defaultValue = "10")Integer size) {

        return ResponseEntity.ok().body(service.getByUserId(userId, PageRequest.of(page, size)).stream()
                .map(obj -> mapper.map(obj, PostDTO.class)).collect(Collectors.toList()));

    }
}
