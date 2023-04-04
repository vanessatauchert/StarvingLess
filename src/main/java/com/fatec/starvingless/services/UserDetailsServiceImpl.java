package com.fatec.starvingless.services;

import com.fatec.starvingless.entities.User;
import com.fatec.starvingless.repositories.UserRepository;
import com.fatec.starvingless.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent()) {
            return new UserSS(user.get().getId(), user.get().getEmail(), user.get().getPassword());
        }
        throw new UsernameNotFoundException(email);

    }
}
