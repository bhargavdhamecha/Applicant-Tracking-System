package com.trackingservices.repository;

import com.trackingservices.model.RejectionReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RejectionReasonRepository extends JpaRepository<RejectionReason,Long> {

}
