package com.training.rledenev.model;

import com.training.rledenev.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role;

    @Email
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @OneToMany(
            mappedBy = "assignee",
            cascade = CascadeType.ALL
    )
    private Set<Ticket> assignedTickets = new HashSet<>();

    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL
    )
    private Set<Ticket> ownedTickets = new HashSet<>();

    @OneToMany(
            mappedBy = "approver",
            cascade = CascadeType.ALL
    )
    private Set<Ticket> approvedTickets = new HashSet<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private Set<Feedback> feedbacks = new HashSet<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private Set<History> histories = new HashSet<>();

    public void addAssignedTicket(Ticket ticket) {
        this.assignedTickets.add(ticket);
        ticket.setAssignee(this);
    }

    public void removeAssignedTicket(Ticket ticket) {
        this.assignedTickets.remove(ticket);
        ticket.setAssignee(null);
    }

    public void addOwnedTicket(Ticket ticket) {
        this.ownedTickets.add(ticket);
        ticket.setOwner(this);
    }

    public void removeOwnedTicket(Ticket ticket) {
        this.ownedTickets.remove(ticket);
        ticket.setOwner(null);
    }

    public void addApprovedTicket(Ticket ticket) {
        this.approvedTickets.add(ticket);
        ticket.setApprover(this);
    }

    public void removeApprovedTicket(Ticket ticket) {
        this.approvedTickets.remove(ticket);
        ticket.setApprover(null);
    }

    //only Employee?
    public void addFeedback(Feedback feedback) {
        this.feedbacks.add(feedback);
        feedback.setUser(this);
    }

    public void removeFeedback(Feedback feedback) {
        this.feedbacks.remove(feedback);
        feedback.setUser(null);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setUser(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setUser(null);
    }

    public void addHistory(History history) {
        this.histories.add(history);
        history.setUser(this);
    }

    public void removeHistory(History history) {
        this.histories.remove(history);
        history.setUser(null);
    }

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<Ticket> getAssignedTickets() {
        return assignedTickets;
    }

    public User setAssignedTickets(Set<Ticket> assignedTickets) {
        this.assignedTickets = assignedTickets;
        return this;
    }

    public Set<Ticket> getOwnedTickets() {
        return ownedTickets;
    }

    public User setOwnedTickets(Set<Ticket> ownedTickets) {
        this.ownedTickets = ownedTickets;
        return this;
    }

    public Set<Ticket> getApprovedTickets() {
        return approvedTickets;
    }

    public User setApprovedTickets(Set<Ticket> approvedTickets) {
        this.approvedTickets = approvedTickets;
        return this;
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public User setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
        return this;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public User setComments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Set<History> getHistories() {
        return histories;
    }

    public User setHistories(Set<History> histories) {
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
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                '}';
    }
}
