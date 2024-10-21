package com.descomplica.FrameBlog.services;

import com.descomplica.FrameBlog.models.Post;
import com.descomplica.FrameBlog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository repository;

    public Post getPostById(Long id) {
        return verifyIfExists(id);
    }

//    List<Post> findAllByUser(Long userId) {
//        return repository.findAllByUserId(userId);
//    }

    public List<Post> findAllPosts() {
        // TODO: verify userRole by token
        return repository.findAll();
    }

    public Post newPost(Post newPost) {
        try {
            return repository.save(newPost);
        } catch(IllegalArgumentException exception ) {
            throw new IllegalArgumentException("Failed to save post : " + exception.getMessage());
        }
    }

    public Post editPost(Post post) {
        var existingPost = verifyIfExists(post.getId());

        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        existingPost.setActive(post.isActive());
        existingPost.setUserId(existingPost.getUserId());
        existingPost.setDeleted(post.isDeleted());

        return repository.save(existingPost);
    }

    public void deletePost(Post post) {
        var existingPost = verifyIfExists(post.getId());

        repository.delete(existingPost);
    }

    Post verifyIfExists(Long postId) {
        return repository.findById(postId).orElseThrow(() -> new IllegalArgumentException("post not found"));
    }
}
