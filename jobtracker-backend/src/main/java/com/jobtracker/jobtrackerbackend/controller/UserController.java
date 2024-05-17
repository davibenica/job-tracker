package com.jobtracker.jobtrackerbackend.controller;

import com.jobtracker.jobtrackerbackend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.jobtracker.jobtrackerbackend.reposotory.userReposotory;
import java.util.Map;
import java.util.Optional;

// TODO: check if email is valid
// TODO: hash password
@Controller
@RequestMapping("/user")
public class UserController {
    private final userReposotory userReposotory;

    @Autowired
    public UserController(userReposotory userReposotory) {
        this.userReposotory = userReposotory;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        System.out.println("Getting user with id: " + id);
        Optional<User> user = userReposotory.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> body){
        String name = body.get("username");
        String password = body.get("password");
        String email = body.get("email");

        if (name == null || password == null || email == null) {
            return ResponseEntity.badRequest().body("Missing required fields");
        }
        // Check if user already exists
        if (userReposotory.findByUsername(name) != null) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        // Check if email already exists
        if (userReposotory.findByEmail(email) != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        User user = new User(name, password, email);
        userReposotory.save(user);
        return ResponseEntity.ok(user);


    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body){
        String name = body.get("username");
        String password = body.get("password");

        if (name == null || password == null) {
            return ResponseEntity.badRequest().body("Missing required fields");
        }
        User user = userReposotory.findByUsername(name);
        if (user == null) {
            return ResponseEntity.badRequest().body("User does not exist");
        }
        if (!user.getPassword().equals(password)) {
            return ResponseEntity.badRequest().body("Incorrect password");
        }
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        userReposotory.deleteById(id);
        return ResponseEntity.ok("User deleted");
    }









}
