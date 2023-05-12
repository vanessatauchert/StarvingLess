package com.fatec.starvingless.repositories;

import com.fatec.starvingless.entities.Comment;
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
public interface CommentRepository extends JpaRepository<Comment, Long>{

//    @Query(value = "select * from comments u where u.postId=:postId ", nativeQuery = true)
//    public List<Comment> listByPostId(@Param("postId") long postId);
    Page<Comment> findByPostId(long postId, Pageable pageable);
    Page<Comment> findByUserId(long userId, Pageable pageable);

    List<Comment> findAllByPostId(Long postId);
}
