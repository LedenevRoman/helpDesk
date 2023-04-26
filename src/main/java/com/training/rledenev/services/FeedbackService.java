package com.training.rledenev.services;

import com.training.rledenev.dto.FeedbackDto;

public interface FeedbackService {

    void save(FeedbackDto feedbackDto, Long ticketId);
}
