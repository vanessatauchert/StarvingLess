package com.fatec.starvingless.services;

import com.fatec.starvingless.dto.CommentDTO;
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

    public Comment findById(Long id) {
        Optional<Comment> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("ID not found!"));
    }

    public Page<Comment> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Comment create(CommentDTO commentDTO){
        commentDTO.setId(null);
        return repository.save(mapper.map(commentDTO, Comment.class));
    }


    public Comment update(CommentDTO commentDTO) {
        Comment post = repository.findById(commentDTO.getId()).orElseThrow(() -> new
                ObjectNotFoundException("User not found."));

        Comment updatedPost = mapper.map(commentDTO, Comment.class);
        return mapper.map(repository.save(updatedPost), Comment.class);
    }

    public void delete(Long id){
        findById(id);
        repository.deleteById(id);
    }

    public Page<Comment> getByPostId(Long postId, Pageable pageable){
        Post c = post.findById(postId).orElseThrow(() -> new
                ObjectNotFoundException("Post not found."));
        return repository.findByPostId(postId, pageable);
    }

    public Page<Comment> getByUserId(Long userId, Pageable pageable){
        User c = user.findById(userId).orElseThrow(() -> new
                ObjectNotFoundException("User not found."));
        return repository.findByUserId(userId, pageable);
    }
}
