package com.trackingservices.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
public class PermissionDTO implements Serializable {
    private String name;

    public PermissionDTO(String name) {
        this.name = name;
    }
}
