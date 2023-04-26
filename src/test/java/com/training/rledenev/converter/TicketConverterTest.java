package com.training.rledenev.converter;

import com.training.rledenev.dto.AttachmentDto;
import com.training.rledenev.dto.FeedbackDto;
import com.training.rledenev.dto.TicketDto;
import com.training.rledenev.factory.TicketDtoFactory;
import com.training.rledenev.factory.TicketFactory;
import com.training.rledenev.factory.UserFactory;
import com.training.rledenev.model.Attachment;
import com.training.rledenev.model.Feedback;
import com.training.rledenev.model.Ticket;
import com.training.rledenev.model.User;
import com.training.rledenev.provider.UserProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class TicketConverterTest {

    @Mock
    private AttachmentConverter attachmentConverter;

    @Mock
    private FeedbackConverter feedbackConverter;

    @Mock
    private UserProvider userProvider;

    @InjectMocks
    private TicketConverter ticketConverter;

    @Test
    void fromEntity() {
        Ticket ticket = TicketFactory.getTicketForConverter();
        User user = UserFactory.getUserForConverter();
        when(userProvider.getCurrentUser()).thenReturn(user);
        when(attachmentConverter.fromEntity(any(Attachment.class))).thenReturn(new AttachmentDto());
        when(feedbackConverter.fromEntity(any(Feedback.class))).thenReturn(new FeedbackDto());
        TicketDto expected = TicketDtoFactory.getTicketDto();

        TicketDto actual = ticketConverter.fromEntity(ticket);

        assertEquals(expected, actual);
    }
}