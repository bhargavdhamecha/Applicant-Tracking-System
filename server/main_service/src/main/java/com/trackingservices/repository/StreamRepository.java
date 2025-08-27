package com.trackingservices.repository;

import com.trackingservices.model.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StreamRepository extends JpaRepository<Stream,Long> {
}
