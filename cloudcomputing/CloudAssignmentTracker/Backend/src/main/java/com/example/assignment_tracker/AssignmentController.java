package com.example.assignment_tracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
import java.util.Arrays;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AssignmentController {
@Autowired
//private AssignmentService AssignmentService;

    @GetMapping("/api/assignments")
    public List<String> getAssignments() {
        return Arrays.asList("Cloud Project Part 1", "Database Lab 3", "System Design Essay");
       // return AssignmentService.getAllAssignments();
    }
}
