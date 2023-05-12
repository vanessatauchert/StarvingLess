package com.fatec.starvingless.services;

import com.fatec.starvingless.dto.PostDTO;
import com.fatec.starvingless.entities.Comment;
import com.fatec.starvingless.entities.Post;
import com.fatec.starvingless.entities.User;
import com.fatec.starvingless.repositories.PostRepository;
import com.fatec.starvingless.repositories.UserRepository;
import com.fatec.starvingless.services.exceptions.ObjectNotFoundException;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PostRepository repository;

    @Autowired
    private UserRepository user;

//    public Post findById(Long id) {
//        Optional<Post> obj = repository.findById(id);
//        return obj.orElseThrow(()-> new ObjectNotFoundException("ID not found!"));
//    }

    public Post findById(Long id) {
        Optional<Post> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("ID not found!"));
    }

    public Page<Post> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

//    public Post create(PostDTO postDTO){
//        postDTO.setId(null);
//        return repository.save(mapper.map(postDTO, Post.class));
//    }

    public Post create(PostDTO postDTO){
        postDTO.setId(null); // Garante que o ID seja gerado automaticamente pelo banco de dados
        return repository.save(mapper.map(postDTO, Post.class));
    }

    public Post update(PostDTO postDTO) {
        Post post = repository.findById(postDTO.getId()).orElseThrow(() -> new
                ObjectNotFoundException("User not found."));

        Post updatedPost = mapper.map(postDTO, Post.class);
        return mapper.map(repository.save(updatedPost), Post.class);
    }

    public void delete(Long id){
        findById(id);
        repository.deleteById(id);
    }

    public Page<Post> getByUserId(Long userId, Pageable pageable){
        User c = user.findById(userId).orElseThrow(() -> new
                ObjectNotFoundException("User not found."));
        return repository.findByUserId(userId, pageable);
    }
}
