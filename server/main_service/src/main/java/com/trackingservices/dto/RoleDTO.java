package com.trackingservices.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trackingservices.model.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO implements Serializable {
    private Long id;
    @NotNull(message = "role name is not specified")
    private String rolename;
    private List<Permission> permissions;
}
