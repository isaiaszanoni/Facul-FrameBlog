package com.descomplica.FrameBlog.repositories;

import com.descomplica.FrameBlog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findUserById(Long id);

//    User save();
}
