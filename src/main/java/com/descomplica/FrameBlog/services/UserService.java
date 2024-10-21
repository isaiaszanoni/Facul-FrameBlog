package com.descomplica.FrameBlog.services;

import com.descomplica.FrameBlog.exception.ResourceAlreadyExistsException;
import com.descomplica.FrameBlog.models.User;
import com.descomplica.FrameBlog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidAttributesException;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    // TODO: get user email by token and permit just for admin
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(Long userId) {
        return repository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public String save(User requestUser) throws InvalidAttributesException {
        var existingUser = repository.findByEmail(requestUser.getEmail());
        if (existingUser.isPresent()) {
            throw new ResourceAlreadyExistsException("User already exists");
        }
        try {
            var studentSaved = repository.save(requestUser);
            return "new user saved successfully : " + studentSaved.getId();
        } catch (IllegalArgumentException err) {
            throw new RuntimeException("Erro ao criar novo usuário. Por favor, tente novamente mais tarde ou acesse o suporte");
        }
    }

    // TODO: add mapper
    public String update(User user) {
        var existingUser = repository.findUserById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setPrivateRollEnum(user.getPrivateRollEnum());

        var response = repository.save(existingUser);
        if (Objects.nonNull(response.getId())) {
            return "User updated with id: " + response.getId();
        }

        throw new RuntimeException("Failed to update User");
    }

    public void deleteUserPermanent(User user) {
        var existingUser = repository.findUserById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        repository.delete(existingUser);
    }
}
