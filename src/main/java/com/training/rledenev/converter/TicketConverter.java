package com.training.rledenev.converter;

import com.training.rledenev.dto.TicketDto;
import com.training.rledenev.model.Category;
import com.training.rledenev.model.Ticket;
import com.training.rledenev.model.User;
import com.training.rledenev.provider.UserProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TicketConverter {
    private final AttachmentConverter attachmentConverter;
    private final FeedbackConverter feedbackConverter;
    private final UserProvider userProvider;


    public TicketConverter(AttachmentConverter attachmentConverter, FeedbackConverter feedbackConverter, UserProvider userProvider) {
        this.attachmentConverter = attachmentConverter;
        this.feedbackConverter = feedbackConverter;
        this.userProvider = userProvider;
    }

    public TicketDto fromEntity(Ticket ticket) {
        if (ticket == null) {
            return new TicketDto();
        }
        User user = userProvider.getCurrentUser();
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(ticket.getId())
                .setName(ticket.getName())
                .setCreatedOn(ticket.getCreatedOn())
                .setStatus(ticket.getStatus().toString())
                .setDescription(ticket.getDescription())
                .setDesiredResolutionDate(ticket.getDesiredResolutionDate())
                .setCategoryId(Optional.ofNullable(ticket.getCategory())
                        .map(Category::getId)
                        .orElse(0L))
                .setUrgencyName(ticket.getUrgency().toString())
                .setOwnerName(Optional.ofNullable(ticket.getOwner())
                        .map(User::getEmail)
                        .orElse(""))
                .setApproverName(Optional.ofNullable(ticket.getApprover())
                        .map(User::getEmail)
                        .orElse("Not approved"))
                .setAssigneeName(Optional.ofNullable(ticket.getAssignee())
                        .map(User::getEmail)
                        .orElse("Not assigned"))
                .setAttachmentsDto(ticket.getAttachments()
                        .stream()
                        .map(attachmentConverter::fromEntity)
                        .collect(Collectors.toList()))
                .setActions(Optional.ofNullable(user.getRole())
                        .map(role -> role.getActions(ticket))
                        .orElse(new ArrayList<>()))
                .setAuthorizedUserRole(user.getRole())
                .setFeedbackDto(feedbackConverter.fromEntity(ticket.getFeedback()))
                .setAuthorizedUserName(user.getEmail());

        return ticketDto;
    }
}
