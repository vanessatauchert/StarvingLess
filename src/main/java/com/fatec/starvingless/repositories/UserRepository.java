package com.fatec.starvingless.repositories;

import com.fatec.starvingless.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByCpf(String cpf);
//    Optional<User> userOptional(String email);

    User findByEmail(String email);
}
