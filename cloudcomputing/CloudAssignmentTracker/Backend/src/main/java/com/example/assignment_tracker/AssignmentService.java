package com.example.assignment_tracker;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
 
@Service
public class AssignmentService {
 
    @Autowired
    private AssignmentRepository assignmentRepository;
 
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAllByOrderByDueDateAsc();
    }
 
    public Optional<Assignment> getAssignmentById(Long id) {
        return assignmentRepository.findById(id);
    }
 
    public Assignment createAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }
 
    public Optional<Assignment> updateAssignment(Long id, Assignment updated) {
        return assignmentRepository.findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setDescription(updated.getDescription());
            existing.setDueDate(updated.getDueDate());
            existing.setStatus(updated.getStatus());
            return assignmentRepository.save(existing);
        });
    }
 
    public boolean deleteAssignment(Long id) {
        if (!assignmentRepository.existsById(id)) {
            return false;
        }
        assignmentRepository.deleteById(id);
        return true;
    }
}