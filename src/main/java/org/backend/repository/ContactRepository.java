package org.backend.repository;

import org.backend.entity.ContactForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<ContactForm, Long> {
    List<ContactForm> findBySubmittedAtAfter(LocalDateTime dateTime);
    
    // Simple method-based queries instead of JPQL
    List<ContactForm> findByStatusAndHiddenFalse(String status);
    List<ContactForm> findByHiddenFalse();
    List<ContactForm> findByStatus(String status);
}