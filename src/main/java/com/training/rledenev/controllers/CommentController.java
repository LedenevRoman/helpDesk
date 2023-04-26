package com.training.rledenev.controllers;

import com.training.rledenev.dto.CommentDto;
import com.training.rledenev.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{ticketId}")
    public CommentDto save(@PathVariable(value = "ticketId") Long ticketId,
                                     @Valid @RequestBody CommentDto commentDto) {
        return commentService.save(commentDto, ticketId);
    }

    @GetMapping("/all/{ticketId}")
    public ResponseEntity<List<CommentDto>> getAll(@PathVariable Long ticketId) {
        List<CommentDto> comments = commentService.getAll(ticketId);
        return ResponseEntity.ok(comments);
    }
}
