package com.trackingservices.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRequest implements Serializable {
    private Long id;
    @NotNull(message = "permission name should not be null")
    private String name;
    private Long createdBy;
    private Long updatedBy;
}
