package com.trackingservices.repository;

import com.trackingservices.dto.PermissionDTO;
import com.trackingservices.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "SELECT new com.trackingservices.dto.PermissionDTO(p.name) FROM Role r\n" +
                   "INNER  join r.permissions p\n" +
                   "WHERE r.rolename = :name")
    Optional<List<PermissionDTO>> findPermissionByRolename(@Param("name") String name);
}
