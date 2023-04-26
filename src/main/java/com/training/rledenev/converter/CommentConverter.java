package com.training.rledenev.converter;

import com.training.rledenev.dto.CommentDto;
import com.training.rledenev.model.Comment;
import com.training.rledenev.model.User;
import com.training.rledenev.provider.LocalDateProvider;
import com.training.rledenev.provider.UserProvider;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommentConverter {
    private final UserProvider userProvider;
    private final LocalDateProvider localDateProvider;

    public CommentConverter(UserProvider userProvider, LocalDateProvider localDateProvider) {
        this.userProvider = userProvider;
        this.localDateProvider = localDateProvider;
    }

    public Comment fromDto(CommentDto commentDto) {
        if (commentDto == null) {
            return new Comment();
        }
        User user = userProvider.getCurrentUser();
        Comment comment = new Comment();
        comment.setUser(user)
                .setText(commentDto.getText())
                .setDate(localDateProvider.getCurrentLocalDate());
        return comment;
    }

    public CommentDto fromEntity(Comment comment) {
        if (comment == null) {
            return new CommentDto();
        }
        CommentDto commentDto = new CommentDto();
        commentDto.setUserEmail(Optional.ofNullable(comment.getUser())
                        .map(User::getEmail)
                        .orElse(""))
                .setText(comment.getText())
                .setDate(comment.getDate());
        return commentDto;
    }
}
