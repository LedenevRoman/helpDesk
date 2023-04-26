package com.training.rledenev.converter;

import com.training.rledenev.dto.FeedbackDto;
import com.training.rledenev.enums.Rate;
import com.training.rledenev.model.Feedback;
import com.training.rledenev.model.User;
import com.training.rledenev.provider.LocalDateProvider;
import com.training.rledenev.provider.UserProvider;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
public class FeedbackConverter {
    private final UserProvider userProvider;
    private final LocalDateProvider localDateProvider;

    public FeedbackConverter(UserProvider userProvider, LocalDateProvider localDateProvider) {
        this.userProvider = userProvider;
        this.localDateProvider = localDateProvider;
    }

    public Feedback fromDto(FeedbackDto feedbackDto) {
        if (feedbackDto == null) {
            return new Feedback();
        }
        User user = userProvider.getCurrentUser();
        Feedback feedback = new Feedback();
        feedback.setUser(user)
                .setDate(localDateProvider.getCurrentLocalDate())
                .setRate(getRate(feedbackDto.getRateName()))
                .setText(feedbackDto.getText());
        return feedback;
    }

    public FeedbackDto fromEntity(Feedback feedback) {
        if (feedback == null) {
            return new FeedbackDto();
        }
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setId(feedback.getId())
                .setRateName(getRateName(feedback.getRate()))
                .setText(feedback.getText());
        return feedbackDto;
    }

    private Rate getRate(String rateName) {
        return Rate.valueOf(Optional.ofNullable(rateName)
                        .map(this::getEnumName)
                        .orElse(Rate.VERY_GOOD.name()));
    }

    private String getEnumName(String rateName) {
        String spaceRegex = "\\s";
        String underscore = "_";
        return rateName.replaceAll(spaceRegex, underscore).toUpperCase(Locale.ROOT);
    }

    private String getRateName(Rate rate) {
        return Optional.ofNullable(rate)
                .map(Rate::toString)
                .orElse("");
    }
}
