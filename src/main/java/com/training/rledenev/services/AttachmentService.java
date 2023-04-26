package com.training.rledenev.services;

import com.training.rledenev.model.Attachment;

import java.util.List;

public interface AttachmentService {

    Attachment findAttachmentById(Long id);

    void addAttachmentsList(Long ticketId, List<Attachment> attachments);

    void removeAttachment(Long ticketId, Long attachmentId);
}
