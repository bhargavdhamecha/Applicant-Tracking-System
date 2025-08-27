package com.trackingservices.repository;

import com.trackingservices.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission,Long> {

}
