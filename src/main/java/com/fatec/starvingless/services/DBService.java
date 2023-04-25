package com.fatec.starvingless.services;

import com.fatec.starvingless.dto.UserDTO;
import com.fatec.starvingless.entities.Role;
import com.fatec.starvingless.entities.User;
import com.fatec.starvingless.repositories.CommentRepository;
import com.fatec.starvingless.repositories.PostRepository;
import com.fatec.starvingless.repositories.RoleRepository;
import com.fatec.starvingless.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DBService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    public void instanceDB(){
//        Role adminRole = new Role();
//        adminRole.setNome("ROLE_ADMIN");
//        roleRepository.save(adminRole);
//
//        User user1 = new User(null, "John", "Wick", "123.456.789-12", "Baker Street",
//                encoder.encode("123456789"), "john@email.com", "(11)91234-5678", "27/10/2021");
//        user1.setRoles(Collections.singletonList(adminRole));
//        userRepository.save(user1);
    }

//    public void criarRole() {
//        Role roleAdmin = new Role();
//        roleAdmin.setNome("ROLE_ADMIN");
//    }
}
