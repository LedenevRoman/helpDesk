package com.training.rledenev.controllers;

import com.training.rledenev.converter.AttachmentConverter;
import com.training.rledenev.model.Attachment;
import com.training.rledenev.services.AttachmentService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/attachment")
public class AttachmentController {

    private final AttachmentService attachmentService;
    private final AttachmentConverter attachmentConverter;

    public AttachmentController(AttachmentService attachmentService, AttachmentConverter attachmentConverter) {
        this.attachmentService = attachmentService;
        this.attachmentConverter = attachmentConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable Long id) {
        final Attachment attachment = attachmentService.findAttachmentById(id);

        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        String mimeType = fileTypeMap.getContentType(attachment.getName());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"")
                .body(new ByteArrayResource(attachment.getBlob()));
    }

    @PostMapping("/add/{ticketId}")
    public ResponseEntity<Void> addAttachment(@PathVariable Long ticketId,
                                              @RequestParam("file") MultipartFile[] multipartFiles) {
        List<Attachment> attachments = Arrays.stream(multipartFiles)
                .map(attachmentConverter::fromMultipartFile)
                .collect(Collectors.toList());
        attachmentService.addAttachmentsList(ticketId, attachments);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeAttachment(@RequestParam(value = "ticketId") Long ticketId,
                                                 @RequestParam(value = "attachmentId") Long attachmentId) {
        attachmentService.removeAttachment(ticketId, attachmentId);
        return ResponseEntity.ok().build();
    }
}
