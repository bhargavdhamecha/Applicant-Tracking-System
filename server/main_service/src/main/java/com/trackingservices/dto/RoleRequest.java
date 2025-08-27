package com.trackingservices.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trackingservices.model.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest implements Serializable {
    private Long id;
    @NotNull(message = "role name is not specified")
    private String rolename;
    private Long createdBy;
    private Long updatedBy;
    private List<Permission> permissions;

}
