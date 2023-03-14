package com.fatec.starvingless.services;

import com.fatec.starvingless.dto.UserDTO;
import com.fatec.starvingless.entities.User;
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
public class UserService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserRepository repository;

    public User findById(Long id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("ID not found!"));
    }

    public Page<User> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public User create(UserDTO userDTO){
        userDTO.setId(null);
        repository.findByCpf(userDTO.getCpf()).ifPresent(u -> { throw new
                UserAlreadyExistsException("CPF already exists.");});
        repository.findByEmail(userDTO.getEmail()).ifPresent(u -> { throw new
                UserAlreadyExistsException("Email already exists.");});
        return repository.save(mapper.map(userDTO, User.class));
    }


    public User update(UserDTO userDTO) {
        User user = repository.findById(userDTO.getId()).orElseThrow(() -> new
                ObjectNotFoundException("User not found."));

        repository.findByCpf(userDTO.getCpf()).filter(u -> !u.getId().equals(user.getId()))
                .ifPresent(u -> { throw new UserAlreadyExistsException("CPF already exists."); });

        repository.findByEmail(userDTO.getEmail()).filter(u -> !u.getId().equals(user.getId()))
                .ifPresent(u -> { throw new UserAlreadyExistsException("Email already exists."); });

        User updatedUser = mapper.map(userDTO, User.class);
        updatedUser.setPassword(user.getPassword()); //preserve original password
        return mapper.map(repository.save(updatedUser), User.class);
    }

    public void delete(Long id){
        findById(id);
        repository.deleteById(id);
    }
}
