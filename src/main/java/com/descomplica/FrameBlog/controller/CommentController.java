package com.descomplica.FrameBlog.controller;

import com.descomplica.FrameBlog.models.Comment;
import com.descomplica.FrameBlog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService service;

    @GetMapping("/{commentId}")
    ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) {
        try {
            return ResponseEntity.ok(service.getCommentById(commentId));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    ResponseEntity<Object> createComment(@RequestBody Comment newComment) {
        try {
            return ResponseEntity.ok(service.newComment(newComment));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @PutMapping()
    ResponseEntity<Object> updateComment(@RequestBody Comment comment) {
        try {
            return ResponseEntity.ok(service.editComment(comment));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @DeleteMapping("/{commentId}")
    ResponseEntity<Object> deleteComment(@PathVariable Long commentId) {
        try {
            service.deleteComment(commentId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
}
