package com.descomplica.FrameBlog.consumers;

import com.descomplica.FrameBlog.models.Comment;
import com.descomplica.FrameBlog.models.User;
import com.descomplica.FrameBlog.repositories.CommentRepository;
import com.descomplica.FrameBlog.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CommentConsumer {
    private static final Logger log = LoggerFactory.getLogger(CommentConsumer.class);
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @RabbitListener(queues = "${FrameBlog.rabbitmq.queue-comment}")
    public void receiveMessage(Comment comment) {
        User user = userRepository.findById(comment.getUserId()).orElse(null);
        if (user != null) {
            return;
        }
        log.info("Received message from RabbitMQ - Comment Queue: {}", comment);
        commentRepository.save(comment);
    }
}
