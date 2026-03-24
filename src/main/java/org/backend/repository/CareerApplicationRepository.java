package org.backend.repository;

import org.backend.entity.CareerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerApplicationRepository extends JpaRepository<CareerApplication, Long> {
    List<CareerApplication> findByJobId(Long jobId);
    List<CareerApplication> findByPositionIgnoreCase(String position);
    List<CareerApplication> findByDeletedFalse();
    List<CareerApplication> findByDeletedTrue();
    List<CareerApplication> findByJobIdAndDeletedFalse(Long jobId);
    List<CareerApplication> findByPositionIgnoreCaseAndDeletedFalse(String position);
}
