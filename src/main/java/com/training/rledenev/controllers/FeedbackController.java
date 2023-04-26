package com.training.rledenev.controllers;

import com.training.rledenev.dto.FeedbackDto;
import com.training.rledenev.services.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/new")
    public ResponseEntity<Void> save(@RequestParam(value = "ticketId") Long ticketId,
                               @RequestBody FeedbackDto feedbackDto) {
        feedbackService.save(feedbackDto, ticketId);
        return ResponseEntity.ok().build();
    }
}
