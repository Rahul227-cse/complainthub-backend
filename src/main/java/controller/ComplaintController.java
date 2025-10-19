package com.complainthub.controller;

import com.complainthub.model.Complaint;
import com.complainthub.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintRepository complaintRepository;

    @GetMapping
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = complaintRepository.findAll();
        return ResponseEntity.ok(complaints);
    }

    @GetMapping("/{complaintId}")
    public ResponseEntity<Complaint> getComplaintById(@PathVariable String complaintId) {
        Complaint complaint = complaintRepository.findByComplaintId(complaintId);
        if (complaint != null) {
            return ResponseEntity.ok(complaint);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Complaint> createComplaint(@RequestBody Complaint complaint) {
        String complaintId = "COMP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        complaint.setComplaintId(complaintId);
        complaint.setStatus("pending");
        complaint.setCreatedDate(LocalDate.now().toString());

        Complaint savedComplaint = complaintRepository.save(complaint);
        return ResponseEntity.ok(savedComplaint);
    }

    @PutMapping("/{complaintId}/status")
    public ResponseEntity<Complaint> updateComplaintStatus(
            @PathVariable String complaintId,
            @RequestParam String status) {

        Complaint existingComplaint = complaintRepository.findByComplaintId(complaintId);
        if (existingComplaint != null) {
            existingComplaint.setStatus(status);
            Complaint updatedComplaint = complaintRepository.save(existingComplaint);
            return ResponseEntity.ok(updatedComplaint);
        }
        return ResponseEntity.notFound().build();
    }
}