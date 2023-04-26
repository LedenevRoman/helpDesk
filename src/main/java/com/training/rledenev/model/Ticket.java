package com.training.rledenev.model;

import com.training.rledenev.enums.Status;
import com.training.rledenev.enums.Urgency;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "TICKETS")
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATED_ON")
    private LocalDate createdOn;

    @Column(name = "DESIRED_RESOLUTION_DATE")
    private LocalDate desiredResolutionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Enumerated(STRING)
    @Column(name = "STATUS")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @Enumerated(STRING)
    @Column(name = "URGENCY")
    private Urgency urgency;

    @ManyToOne(fetch = FetchType.LAZY)
    private User approver;

    @OneToOne(
            fetch = FetchType.LAZY,
            mappedBy = "ticket")
    private Feedback feedback;

    @OneToMany(
            mappedBy = "ticket",
            cascade = CascadeType.ALL
    )
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(
            mappedBy = "ticket",
            cascade = CascadeType.ALL
    )
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(
            mappedBy = "ticket",
            cascade = CascadeType.ALL
    )
    private List<History> histories = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setTicket(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setTicket(null);
    }

    public void addHistory(History history) {
        this.histories.add(history);
        history.setTicket(this);
    }

    public void removeHistory(History history) {
        this.histories.remove(history);
        history.setTicket(null);
    }

    public void addAttachment(Attachment attachment) {
        this.attachments.add(attachment);
        attachment.setTicket(this);
    }

    public void removeAttachment(Attachment attachment) {
        this.attachments.remove(attachment);
        attachment.setTicket(null);
    }

    public Long getId() {
        return id;
    }

    public Ticket setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Ticket setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Ticket setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public Ticket setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public LocalDate getDesiredResolutionDate() {
        return desiredResolutionDate;
    }

    public Ticket setDesiredResolutionDate(LocalDate desiredResolutionDate) {
        this.desiredResolutionDate = desiredResolutionDate;
        return this;
    }

    public User getAssignee() {
        return assignee;
    }

    public Ticket setAssignee(User assignee) {
        this.assignee = assignee;
        return this;
    }

    public User getOwner() {
        return owner;
    }

    public Ticket setOwner(User owner) {
        this.owner = owner;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Ticket setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Ticket setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public Ticket setUrgency(Urgency urgency) {
        this.urgency = urgency;
        return this;
    }

    public User getApprover() {
        return approver;
    }

    public Ticket setApprover(User approver) {
        this.approver = approver;
        return this;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public Ticket setFeedback(Feedback feedback) {
        this.feedback = feedback;
        return this;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Ticket setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public Ticket setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }

    public List<History> getHistories() {
        return histories;
    }

    public Ticket setHistories(List<History> histories) {
        this.histories = histories;
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
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
