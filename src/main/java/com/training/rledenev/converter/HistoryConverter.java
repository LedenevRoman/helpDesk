package com.training.rledenev.converter;

import com.training.rledenev.dto.HistoryDto;
import com.training.rledenev.model.History;
import com.training.rledenev.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HistoryConverter {
    public HistoryDto fromEntity(History history) {
        if (history == null) {
            return new HistoryDto();
        }
        HistoryDto historyDto = new HistoryDto();
        historyDto.setDate(history.getDate())
                .setEmail(Optional.ofNullable(history.getUser())
                        .map(User::getEmail)
                        .orElse(""))
                .setAction(history.getAction())
                .setDescription(history.getDescription());
        return historyDto;
    }
}
