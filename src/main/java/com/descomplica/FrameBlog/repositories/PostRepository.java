package com.descomplica.FrameBlog.repositories;

import com.descomplica.FrameBlog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
//    List<Post> findAllByUserId(Long id);


}
