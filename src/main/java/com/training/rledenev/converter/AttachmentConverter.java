package com.training.rledenev.converter;

import com.training.rledenev.dto.AttachmentDto;
import com.training.rledenev.exception.WrongFileType;
import com.training.rledenev.exception.WrongFileSize;
import com.training.rledenev.model.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class AttachmentConverter {
    private static final Logger LOGGER = LogManager.getLogger(AttachmentConverter.class);

    public AttachmentDto fromEntity(final Attachment attachment) {
        if (attachment == null) {
            return new AttachmentDto();
        }
        AttachmentDto attachmentDto = new AttachmentDto();
        attachmentDto.setId(attachment.getId())
                .setName(attachment.getName());

        return attachmentDto;
    }

    public Attachment fromMultipartFile(MultipartFile multipartFile) {
        Attachment attachment = new Attachment();
        checkFileType(multipartFile);
        checkFileSize(multipartFile);
        try {
            attachment.setName(multipartFile.getOriginalFilename())
                    .setBlob(multipartFile.getBytes());
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return attachment;
    }

    private void checkFileSize(MultipartFile multipartFile) {
        if (multipartFile.getSize() > 5242880) {
            throw new WrongFileSize("The size of the attached file should not be greater than 5 Mb. " +
                    "Please select another file.");
        }
    }

    private void checkFileType(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String fileExtension = getFileExtension(fileName);
        if (fileExtension == null || !isValidExtension(fileExtension)) {
            throw new WrongFileType("The selected file type is not allowed. " +
                    "Please select a file of one of the following types: pdf, png, doc, docx, jpg, jpeg.");
        }
    }

    private String getFileExtension(String fileName) {
        String fileExtension = null;
        if (fileName != null) {
            String[] fileNameArray = fileName.split("\\.");
            fileExtension = fileNameArray[fileNameArray.length - 1];
        }
        return fileExtension;
    }

    private boolean isValidExtension(String fileExtension) {
        return fileExtension.equals("doc")
                || fileExtension.equals("pdf")
                || fileExtension.equals("png")
                || fileExtension.equals("docx")
                || fileExtension.equals("jpg")
                || fileExtension.equals("jpeg");
    }
}
