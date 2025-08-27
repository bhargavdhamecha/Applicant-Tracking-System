package com.trackingservices.repository;

import com.trackingservices.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity,Long> {
    Optional<FileEntity> findByUserId(Long userId);
}