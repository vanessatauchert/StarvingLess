package com.fatec.starvingless.repositories;

import com.fatec.starvingless.entities.Post;
import com.fatec.starvingless.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

}
