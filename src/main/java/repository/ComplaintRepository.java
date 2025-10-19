package com.complainthub.repository;

import com.complainthub.model.Complaint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends MongoRepository<Complaint, String> {
    Complaint findByComplaintId(String complaintId);
}