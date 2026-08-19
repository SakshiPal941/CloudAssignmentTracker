package com.example.assignment_tracker;
 
import jakarta.persistence.*;
import java.time.LocalDate;
 
@Entity
@Table(name = "assignments")
public class Assignment {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @Column(nullable = false)
    private String title;
 
    @Column
    private String description;
 
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
 
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;
 
    public Assignment() {
        // required no-args constructor for JPA
    }
 
    public Assignment(String title, String description, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }
 
    // --- Getters and setters ---
 
    public Long getId() {
        return id;
    }
 
    public String getTitle() {
        return title;
    }
 
    public void setTitle(String title) {
        this.title = title;
    }
 
    public String getDescription() {
        return description;
    }
 
    public void setDescription(String description) {
        this.description = description;
    }
 
    public LocalDate getDueDate() {
        return dueDate;
    }
 
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
 
    public Status getStatus() {
        return status;
    }
 
    public void setStatus(Status status) {
        this.status = status;
    }
 
    public enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }
}