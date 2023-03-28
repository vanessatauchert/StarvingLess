package com.fatec.starvingless.services;

import com.fatec.starvingless.dto.CommentDTO;
import com.fatec.starvingless.dto.PostDTO;
import com.fatec.starvingless.dto.UserDTO;
import com.fatec.starvingless.entities.Comment;
import com.fatec.starvingless.entities.Comment;
import com.fatec.starvingless.entities.Post;
import com.fatec.starvingless.repositories.CommentRepository;
import com.fatec.starvingless.repositories.UserRepository;
import com.fatec.starvingless.services.exceptions.ObjectNotFoundException;
import com.fatec.starvingless.services.exceptions.UserAlreadyExistsException;
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
}
