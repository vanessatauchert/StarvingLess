package com.fatec.starvingless.controllers;

import com.fatec.starvingless.entities.Comment;
import com.fatec.starvingless.entities.User;
import com.fatec.starvingless.repositories.PostRepository;
import com.fatec.starvingless.repositories.UserRepository;
import com.fatec.starvingless.services.CommentService;
import com.fatec.starvingless.services.UserService;
import com.fatec.starvingless.services.exceptions.ObjectNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

//    @GetMapping("/cm/id" + ID)
//    @Operation(summary = "Find a Comment by Id")
//    public ResponseEntity<CommentDTO> findById(@Valid @PathVariable Long id){
//        return ResponseEntity.ok().body(mapper.map(service.findById(id), CommentDTO.class));
//    }
//
////    @GetMapping("/cm/list")
////    @Operation(summary = "Find all Comments by page")
////    public ResponseEntity<List<CommentDTO>> findAll(@RequestParam(value= "page", defaultValue = "0") int page,
////                                                 @RequestParam(value= "size", defaultValue = "10") int size){
////        return ResponseEntity.ok().body(service.findAll(PageRequest.of(page, size)).stream()
////                .map(obj -> mapper.map(obj, CommentDTO.class)).collect(Collectors.toList()));
////
////    }
//
//    @GetMapping("/cm/list")
//    @Operation(summary = "Find all Comments by page")
//    public ResponseEntity<List<CommentDTO>> findAll(@RequestParam(value= "page", defaultValue = "0") int page,
//                                                    @RequestParam(value= "size", defaultValue = "10") int size){
//        return ResponseEntity.ok().body(service.findAll(PageRequest.of(page, size)).stream()
//                .map(obj -> mapper.map(obj, CommentDTO.class)).collect(Collectors.toList()));
//
//    }
//
////    @PostMapping("/cm/create")
////    @Operation(summary = "Create a Comment")
////    public ResponseEntity<CommentDTO> create(@Valid @RequestBody CommentDTO commentDTO){
////        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("id" + ID)
////                .buildAndExpand(service.create(commentDTO).getId()).toUri();
////        return ResponseEntity.created(uri).build();
////    }
//
//        @PostMapping("/cm/create")
//    @Operation(summary = "Create a Comment")
//    public ResponseEntity<CommentDTO> create(@Valid @RequestBody CommentDTO commentDTO){
//            User user = userService.findById(commentDTO.getUserId());
//            String firstName = user.getFirstName();
//
//            CommentDTO comment = new CommentDTO();
//            comment.setDescription(commentDTO.getDescription());
//            comment.setCreateDate(commentDTO.getCreateDate());
//            comment.setPostId(commentDTO.getPostId());
//
//            comment.setUserId(commentDTO.getUserId());
//            comment.setFirstName(firstName);
//
//            Comment savedComment = service.create(comment);
//
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("id" + ID)
//                .buildAndExpand(savedComment.getId()).toUri();
//
//        CommentDTO savedCommentDTO = new CommentDTO(savedComment, firstName);
//        savedCommentDTO.setFirstName(firstName);
//        return ResponseEntity.created(uri).body(new CommentDTO(savedComment, firstName));
//    }
//
//
//    @PutMapping("/cm/update" + ID)
//    @Operation(summary = "Update a Comment by Id")
//    public ResponseEntity<CommentDTO> updateUser(@PathVariable Long id, @RequestBody @Valid CommentDTO commentDTO) {
//        commentDTO.setId(id);
//        Comment updatedUser = service.update(commentDTO);
//        CommentDTO updatedUserDTO = mapper.map(updatedUser, CommentDTO.class);
//        return ResponseEntity.ok(updatedUserDTO);
//    }
//
//    @DeleteMapping("/cm/delete" + ID)
//    @Operation(summary = "Delete a Comment by Id")
//    public ResponseEntity<CommentDTO> delete(@PathVariable Long id){
//        service.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/cm/post" + POST_ID)
//    @Operation(summary = "Find Comments by Post")
//    public ResponseEntity<List<CommentDTO>> postId(
//            @PathVariable Long postId,
//            @RequestParam(value = "page", defaultValue = "0")Integer page,
//            @RequestParam(value = "size", defaultValue = "10")Integer size) {
//
//        return ResponseEntity.ok().body(service.getByPostId(postId, PageRequest.of(page, size)).stream()
//                .map(obj -> mapper.map(obj, CommentDTO.class)).collect(Collectors.toList()));
//
//    }
//
//    @GetMapping("/cm/user" + USER_ID)
//    @Operation(summary = "Find Comments by User")
//    public ResponseEntity<List<CommentDTO>> userId(
//            @PathVariable Long userId,
//            @RequestParam(value = "page", defaultValue = "0")Integer page,
//            @RequestParam(value = "size", defaultValue = "10")Integer size) {
//
//        return ResponseEntity.ok().body(service.getByUserId(userId, PageRequest.of(page, size)).stream()
//                .map(obj -> mapper.map(obj, CommentDTO.class)).collect(Collectors.toList()));
//
//    }

    @PostMapping("/cm/create")
@Operation(summary = "Create a Comment")
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequest commentRequest) {
        Optional<User> userOptional = userRepository.findById(commentRequest.getUserId());
        if (!userOptional.isPresent()) {
            throw new ObjectNotFoundException("User not found with id " + commentRequest.getUserId());
        }
        User user = userOptional.get();
        Comment comment = new Comment(user);
        comment.setDescription(commentRequest.getDescription());
        comment.setCreateDate(commentRequest.getCreateDate());
        comment.setPost(postRepository.getOne(commentRequest.getPostId()));
        Comment savedComment = service.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @GetMapping("/cm/id" + ID)
@Operation(summary = "Find a Comment by Id")
    public ResponseEntity<Comment> findById(@PathVariable Long id) {
        Optional<Comment> comment = service.findById(id);
        if (comment.isPresent()) {
            return ResponseEntity.ok(comment.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cm/list")
@Operation(summary = "Find all Comments by page")
    public ResponseEntity<List<Comment>> findAll() {
        List<Comment> comments = service.findAll();
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(comments);
        }
    }

    @DeleteMapping("/cm/delete" + ID)
@Operation(summary = "Delete a Comment by Id")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Optional<Comment> comment = service.findById(id);
        if (comment.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cm/post/{postId}")
    @Operation(summary = "Find all Comments by Post Id")
    public ResponseEntity<List<Comment>> findAllByPostId(@PathVariable Long postId) {
        List<Comment> comments = service.findAllByPostId(postId);
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(comments);
        }
    }
}

