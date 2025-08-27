package com.trackingservices.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageEntity implements Serializable {
    @Id
    private Long imageId;

    private String filepath;

    private String fileType;

    private Long userId;
}