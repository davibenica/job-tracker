package com.jobtracker.jobtrackerbackend.controller;

import com.jobtracker.jobtrackerbackend.models.Jobs;
import com.jobtracker.jobtrackerbackend.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.jobtracker.jobtrackerbackend.reposotory.jobRepository;
import com.jobtracker.jobtrackerbackend.reposotory.userReposotory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/job")
public class JobController {
    private final jobRepository jobRepository;
    private final userReposotory userReposotory;


    @Autowired
    public JobController(jobRepository jobRepository, userReposotory userReposotory) {
        this.jobRepository = jobRepository;
        this.userReposotory = userReposotory;
    }
    // Get all jobs for a user
    @GetMapping("/{id}")
    public ResponseEntity<List<Jobs>> getJobById(@PathVariable Integer id) {
        List<Jobs> jobs = jobRepository.findByUserId(id);
        return ResponseEntity.ok(jobs);
    }
    @PostMapping("/create")
    public ResponseEntity<?> createJob(@RequestBody Map<String, String> body){

        String title = body.get("jobTitle");
        String description = body.get("jobDescription");
        Integer userId = Integer.parseInt(body.get("userId"));
        String notes = body.get("notes");
        String status = body.get("status");

        if (title == null || description == null || userId == null || notes == null || status == null) {
            return ResponseEntity.badRequest().body("Missing required fields");
        }
        User user = userReposotory.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User does not exist");
        }
        Jobs job = new Jobs(title, description, notes, status, user);

        jobRepository.save(job);
        return ResponseEntity.ok(job);
    }






}
