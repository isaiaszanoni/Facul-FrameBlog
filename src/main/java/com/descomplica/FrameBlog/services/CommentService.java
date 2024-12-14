package com.descomplica.FrameBlog.services;

import com.descomplica.FrameBlog.models.Comment;
import com.descomplica.FrameBlog.repositories.CommentRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentRepository repository;

    @Autowired
    UserService userService;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Value("${FrameBlog.rabbitmq.exchange}")
    private String exchange;

    @Value("${FrameBlog.rabbitmq.routingkey}")
    private String routingKey;

    public Comment getCommentById(Long id) {
        return verifyAndGetIfExists(id);
    }

    public Comment newComment(Comment newComment) {
        try {
            var user = userService.getUserById(newComment.getUserId());
            newComment.setUserId(user.getId());
//            amqpTemplate.convertAndSend(exchange, routingKey, newComment);
            return repository.save(newComment);
        } catch(Exception exception ) {
            throw new IllegalArgumentException("Failed to save comment : " + exception.getMessage());
        }
    }

    public Comment editComment(Comment comment) {
        var existingComment = verifyAndGetIfExists(comment.getId());

        existingComment.setTitle(comment.getTitle());
        existingComment.setText(comment.getText());

        return repository.save(existingComment);
    }

    public void deleteComment(Long commentId) {
        var existingComment = verifyAndGetIfExists(commentId);

        repository.delete(existingComment);
    }

    private Comment verifyAndGetIfExists(Long commentId) {
        return repository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("comment not found"));
    }
}
