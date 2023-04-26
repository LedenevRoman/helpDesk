package com.training.rledenev.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

public class CreateTicketDto {

    @NotBlank(message = "Name can not be empty")
    @Size(max = 100, message = "Name can not be longer than 100 characters")
    @Pattern(regexp = "[a-z0-9~.\"(),:;<>@\\[\\]!#$%&'*+\\-/=?^_`{|}]*", message = "Name contains invalid characters")
    private String name;

    @Size(max = 500, message = "Description can not be longer than 500 characters")
    @Pattern(regexp = "[a-zA-Z0-9~.\"(),:;<>@\\[\\]!#$%&'*+\\-/=?^_`{|}\\s]*", message = "Description contains invalid characters")
    private String description;

    @Size(max = 500, message = "Comment can not be longer than 500 characters")
    @Pattern(regexp = "[a-zA-Z0-9~.\"(),:;<>@\\[\\]!#$%&'*+\\-/=?^_`{|}\\s]*", message = "Comment contains invalid characters")
    private String commentText;

    private Long categoryId;

    @NotBlank
    private String urgencyName;

    private LocalDate desiredResolutionDate;

    public String getName() {
        return name;
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

    public String getCommentText() {
        return commentText;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDesiredResolutionDate(LocalDate desiredResolutionDate) {
        this.desiredResolutionDate = desiredResolutionDate;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setUrgencyName(String urgencyName) {
        this.urgencyName = urgencyName;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateTicketDto that = (CreateTicketDto) o;
        return Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && Objects.equals(desiredResolutionDate, that.desiredResolutionDate)
                && Objects.equals(categoryId, that.categoryId)
                && Objects.equals(urgencyName, that.urgencyName)
                && commentText.equals(that.commentText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, desiredResolutionDate, categoryId, urgencyName, commentText);
    }

    @Override
    public String toString() {
        return "CreateTicketDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", desiredResolutionDate=" + desiredResolutionDate +
                ", categoryId=" + categoryId +
                ", urgency=" + urgencyName +
                ", commentText='" + commentText + '\'' +
                '}';
    }
}
