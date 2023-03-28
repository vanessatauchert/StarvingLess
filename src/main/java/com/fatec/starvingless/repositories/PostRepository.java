package com.fatec.starvingless.repositories;

import com.fatec.starvingless.entities.Comment;
import com.fatec.starvingless.entities.Post;
import com.fatec.starvingless.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    Page<Post> findByUserId(long userId, Pageable pageable);
//    @Query("SELECT p FROM Post p JOIN FETCH p.comments WHERE p.id = :id")
//    Optional<Post> findByIdWithComments(@Param("id") Long id);
//
//    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.comments")
//    List<Post> findAllWithComments();
}
