package com.fatec.starvingless.services;

import com.fatec.starvingless.dto.UserDTO;
import com.fatec.starvingless.entities.User;
import com.fatec.starvingless.repositories.CommentRepository;
import com.fatec.starvingless.repositories.PostRepository;
import com.fatec.starvingless.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
@Service
public class DBService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void instanceDB(){
        User user1 = new User(null, "John", "Wick", "123.456.789-12", "Baker Street",
                encoder.encode("123456789"), "john@email.com", "(11)91234-5678", "27/10/2021");
        UserDTO userDTO1 = new UserDTO(user1);

        userRepository.save(user1);
    }
}
