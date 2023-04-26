package com.training.rledenev.services;

import com.training.rledenev.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto save(CommentDto commentDto, Long ticketId);

    List<CommentDto> getAll(Long ticketId);
}
