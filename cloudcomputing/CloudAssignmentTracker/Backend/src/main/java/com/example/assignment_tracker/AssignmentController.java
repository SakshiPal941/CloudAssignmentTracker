package com.example.assignment_tracker;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 

@RestController
public class AssignmentController {
 
    @Autowired
    private AssignmentService assignmentService;
 
    @GetMapping("/api/assignments")
    public List<Assignment> getAssignments() {
        return assignmentService.getAllAssignments();
    }
 
    @GetMapping("/api/assignments/{id}")
    public ResponseEntity<Assignment> getAssignment(@PathVariable Long id) {
        return assignmentService.getAssignmentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
 
    @PostMapping("/api/assignments")
    public Assignment createAssignment(@RequestBody Assignment assignment) {
        return assignmentService.createAssignment(assignment);
    }
 
    @PutMapping("/api/assignments/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Long id, @RequestBody Assignment updated) {
        return assignmentService.updateAssignment(id, updated)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
 
    @DeleteMapping("/api/assignments/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        boolean deleted = assignmentService.deleteAssignment(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}