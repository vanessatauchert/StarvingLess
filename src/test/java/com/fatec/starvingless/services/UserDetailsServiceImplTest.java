package com.fatec.starvingless.services;

import com.fatec.starvingless.entities.User;
import com.fatec.starvingless.repositories.UserRepository;
import com.fatec.starvingless.security.UserSS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadUserByUsername() {
        String email = "test@example.com";
        String password = "password123";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        Optional<User> optionalUser = Optional.of(user);

        // Mockando o método findByEmail do UserRepository
        when(userRepository.findByEmail(email)).thenReturn(optionalUser);

        // Chamando o método loadUserByUsername
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Verificando se o usuário retornado é do tipo UserSS
        assertTrue(userDetails instanceof UserSS);

        UserSS userSS = (UserSS) userDetails;

        // Verificando se o email e senha do UserSS são iguais ao email e senha do usuário criado anteriormente
        assertEquals(email, userSS.getUsername());
        assertEquals(password, userSS.getPassword());
    }

//    @Test(expected = UsernameNotFoundException.class)
//    public void testLoadUserByUsernameNotFound() {
//        String email = "test@example.com";
//
//        // Mockando o método findByEmail do UserRepository
//        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
//
//        // Chamando o método loadUserByUsername com um email que não existe no banco de dados
//        userDetailsService.loadUserByUsername(email);
//    }

    @Test
    void testLoadUserByUsernameNotFound() {
        String email = "email_inexistente@example.com";
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(email);
        });
    }


}