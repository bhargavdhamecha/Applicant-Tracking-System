package com.trackingservices.service;

import com.trackingservices.dto.ResponseDTO;
import com.trackingservices.dto.RoleRequest;
import com.trackingservices.exception.ResourceNotFoundException;
import com.trackingservices.model.Role;
import com.trackingservices.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Configuration
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = SQLException.class, isolation = Isolation.SERIALIZABLE)
    public ResponseDTO saveRole(RoleRequest roleRequest) throws HttpServerErrorException.InternalServerError {
        Role role = Role.builder()
                .rolename(roleRequest.getRolename())
                .permissions(roleRequest.getPermissions())
                .createdBy(roleRequest.getCreatedBy())
                .build();
        roleRepository.save(role);
        log.info("Role saved successfully{}", role);
        return ResponseDTO.builder()
                .message("Role saved successfully")
                .build();
    }

    @Transactional(propagation = Propagation.NESTED, rollbackFor = {SQLException.class}, isolation = Isolation.READ_COMMITTED)
    public ResponseDTO updateRole(RoleRequest roleRequest) throws HttpServerErrorException.InternalServerError{
        if(roleRepository.findById(roleRequest.getId()).isPresent()) {
            Role role = Role.builder()
                    .id(roleRequest.getId())
                    .rolename(roleRequest.getRolename())
                    .permissions(roleRequest.getPermissions())
                    .updatedBy(roleRequest.getUpdatedBy())
                    .build();
            roleRepository.save(role);
            log.info("Role updated successfully {}", role);
            return ResponseDTO.builder()
                    .message("Role update successfully")
                    .build();

        }
        else {
            throw new ResourceNotFoundException("role", "roleId", roleRequest.getId());
        }
    }

    public Optional<List<Role>> getAllRole() throws HttpServerErrorException.InternalServerError {
        Optional<List<Role>> roles = Optional.of(roleRepository.findAll());
        return Optional.of(roles.orElse(null));
    }
}
