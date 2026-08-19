package com.example.assignment_tracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
import java.util.Arrays;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AssignmentController {

    @GetMapping("/api/assignments")
    public List<String> getAssignments() {
        return Arrays.asList("Cloud Project Part 1", "Database Lab 3", "System Design Essay");
    }
}
