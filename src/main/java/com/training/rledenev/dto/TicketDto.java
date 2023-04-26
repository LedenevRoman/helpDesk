package com.training.rledenev.dto;

import com.training.rledenev.enums.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class TicketDto {

    private Long id;
    private String name;
    private LocalDate createdOn;
    private String status;
    private String description;
    private LocalDate desiredResolutionDate;
    private Long categoryId;
    private String urgencyName;
    private String ownerName;
    private String approverName;
    private String assigneeName;
    private List<AttachmentDto> attachmentsDto;
    private List<String> actions;
    private FeedbackDto feedbackDto;
    private Role authorizedUserRole;
    private String authorizedUserName;

    public Long getId() {
        return id;
    }

    public TicketDto setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDesiredResolutionDate() {
        return desiredResolutionDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getUrgencyName() {
        return urgencyName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getApproverName() {
        return approverName;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public List<AttachmentDto> getAttachmentsDto() {
        return attachmentsDto;
    }

    public String getName() {
        return name;
    }

    public String getAuthorizedUserName() {
        return authorizedUserName;
    }

    public TicketDto setName(String name) {
        this.name = name;
        return this;
    }

    public TicketDto setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public TicketDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public TicketDto setDesiredResolutionDate(LocalDate desiredResolutionDate) {
        this.desiredResolutionDate = desiredResolutionDate;
        return this;
    }

    public TicketDto setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public TicketDto setUrgencyName(String urgencyName) {
        this.urgencyName = urgencyName;
        return this;
    }

    public TicketDto setOwnerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    public TicketDto setApproverName(String approverName) {
        this.approverName = approverName;
        return this;
    }

    public TicketDto setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
        return this;
    }

    public TicketDto setAttachmentsDto(List<AttachmentDto> attachmentsDto) {
        this.attachmentsDto = attachmentsDto;
        return this;
    }

    public TicketDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public List<String> getActions() {
        return actions;
    }

    public TicketDto setActions(List<String> actions) {
        this.actions = actions;
        return this;
    }

    public Role getAuthorizedUserRole() {
        return authorizedUserRole;
    }

    public TicketDto setAuthorizedUserRole(Role authorizedUserRole) {
        this.authorizedUserRole = authorizedUserRole;
        return this;
    }

    public FeedbackDto getFeedbackDto() {
        return feedbackDto;
    }

    public TicketDto setFeedbackDto(FeedbackDto feedbackDto) {
        this.feedbackDto = feedbackDto;
        return this;
    }

    public TicketDto setAuthorizedUserName(String authorizedUserName) {
        this.authorizedUserName = authorizedUserName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TicketDto ticketDto = (TicketDto) o;
        return Objects.equals(id, ticketDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TicketDto{" +
                "Id=" + id +
                ", name='" + name + '\'' +
                ", createdOn=" + createdOn +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", desiredResolutionDate=" + desiredResolutionDate +
                ", categoryId=" + categoryId +
                ", urgency=" + urgencyName +
                ", ownerName='" + ownerName + '\'' +
                ", approverName='" + approverName + '\'' +
                ", assigneeName='" + assigneeName + '\'' +
                ", attachmentDto=" + attachmentsDto +
                ", actions=" + actions +
                '}';
    }
}
