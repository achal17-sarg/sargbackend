package org.backend.repository;

import org.backend.entity.JourneyInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyInquiryRepository extends JpaRepository<JourneyInquiry, Long> {
    // No code needed here; JpaRepository provides .save(), .findAll(), etc.
}