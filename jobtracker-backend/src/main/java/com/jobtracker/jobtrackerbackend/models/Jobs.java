package com.jobtracker.jobtrackerbackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "jobs")
public class Jobs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    private String jobTitle;
    private String jobDescription;

    private String notes;
    private String status;

    public Jobs(String jobTitle, String jobDescription, String notes, String status, User user) {
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.notes = notes;
        this.status = status;
        this.user = user;
    }

    // No-argument constructor is required for JPA
    public Jobs() {}

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }


    public String getNotes() {
        return notes;
    }

    public String getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }


    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
