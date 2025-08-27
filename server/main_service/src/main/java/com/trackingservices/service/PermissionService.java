package com.trackingservices.service;

import com.trackingservices.dto.PermissionRequest;
import com.trackingservices.dto.ResponseDTO;
import com.trackingservices.exception.ResourceNotFoundException;
import com.trackingservices.model.Permission;
import com.trackingservices.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

import javax.ws.rs.InternalServerErrorException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Configuration
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    public Optional<List<Permission>> getAllPermissions() throws HttpServerErrorException.InternalServerError  {
        Optional<List<Permission>> permissions = Optional.of(permissionRepository.findAll());
        return Optional.of(permissions.orElseThrow(null));
    }

    @Transactional(propagation = Propagation.NESTED, rollbackFor = {SQLException.class}, isolation = Isolation.READ_COMMITTED)
    public ResponseDTO updatePermission(PermissionRequest permissionRequest, Long id) throws HttpServerErrorException.InternalServerError {
        Optional<Permission> permission = permissionRepository.findById(id);
        if (permission.isPresent()) {
            permission.get().setName(permissionRequest.getName());
            permission.get().setUpdatedBy(permissionRequest.getUpdatedBy());
            permissionRepository.save(permission.get());
            log.info("Permission updated to " + permission);
            return ResponseDTO.builder()
                    .message("Permission updated to successfully ")
                    .build();
        } else {
            return  ResponseDTO.builder()
                    .message("Permission is not exits")
                    .build();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = SQLException.class, isolation = Isolation.SERIALIZABLE)
    public ResponseDTO savePermission(PermissionRequest permissionRequest) throws HttpServerErrorException.InternalServerError {

        Permission permission = Permission.builder()
                .name(permissionRequest.getName())
                .createdBy(permissionRequest.getCreatedBy())
                .build();

        permissionRepository.save(permission);
        log.info("complete permission {}", permission);
        return ResponseDTO.builder()
                .message("saved permission ")
                .build();
    }
}
