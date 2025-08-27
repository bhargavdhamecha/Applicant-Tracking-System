package com.Security.dto;

import com.Security.entity.Permission;
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
public class RoleDto implements Serializable {
    @NotNull(message = "role name must be specified")
    private String roleName;
    private List<Permission> permissions;
}
