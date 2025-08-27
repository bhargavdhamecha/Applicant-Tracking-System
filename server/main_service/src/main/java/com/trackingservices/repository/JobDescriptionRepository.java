package com.trackingservices.repository;

import com.trackingservices.model.JobDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobDescriptionRepository extends JpaRepository<JobDescription,Long> {
    @Query(value = "SELECT a.stream, COUNT(a.user) FROM JobDescription a GROUP BY a.stream")
    List<Object[]> countApplicantsByStream();
}
