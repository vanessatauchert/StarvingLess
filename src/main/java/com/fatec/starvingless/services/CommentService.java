package com.fatec.starvingless.services;

import com.fatec.starvingless.entities.Comment;
import com.fatec.starvingless.entities.Post;
import com.fatec.starvingless.entities.User;
import com.fatec.starvingless.repositories.CommentRepository;
import com.fatec.starvingless.repositories.PostRepository;
import com.fatec.starvingless.repositories.UserRepository;
import com.fatec.starvingless.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CommentRepository repository;
    @Autowired
    private PostRepository post;
    @Autowired
    private UserRepository user;

//    public Comment findById(Long id) {
//        Optional<Comment> obj = repository.findById(id);
//        return obj.orElseThrow(()-> new ObjectNotFoundException("ID not found!"));
//    }
//
////    public Page<Comment> findAll(Pageable pageable){
////        return repository.findAll(pageable);
////    }
//
//    public List<Comment> findAll() {
//        return repository.findAll();
//    }
//
//    public Comment create(Comment commentDTO){
//        commentDTO.setId(null);
//        return repository.save(mapper.map(commentDTO, Comment.class));
//    }
//
//
//    public Comment update(Comment commentDTO) {
//        Comment post = repository.findById(commentDTO.getId()).orElseThrow(() -> new
//                ObjectNotFoundException("User not found."));
//
//        Comment updatedPost = mapper.map(commentDTO, Comment.class);
//        return mapper.map(repository.save(updatedPost), Comment.class);
//    }
//
//    public void delete(Long id){
//        findById(id);
//        repository.deleteById(id);
//    }
//
//    public Page<Comment> getByPostId(Long postId, Pageable pageable){
//        Post c = post.findById(postId).orElseThrow(() -> new
//                ObjectNotFoundException("Post not found."));
//        return repository.findByPostId(postId, pageable);
//    }
//
//    public Page<Comment> getByUserId(Long userId, Pageable pageable){
//        User c = user.findById(userId).orElseThrow(() -> new
//                ObjectNotFoundException("User not found."));
//        return repository.findByUserId(userId, pageable);
//    }

    @Autowired
    private CommentRepository commentRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> findAllByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }
}

