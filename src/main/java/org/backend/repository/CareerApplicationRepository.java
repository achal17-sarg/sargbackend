package org.backend.repository;

import org.backend.entity.CareerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerApplicationRepository extends JpaRepository<CareerApplication, Long> {
}