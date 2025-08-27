package com.trackingservices.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="position")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Position implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "position_id")
    private Long positionId;

    @Column(name="position_name")
    private String positionName;

    @Column(name="position_description")
    private String positionDescription;

    @Column(name = "created_time")
    @CreationTimestamp
    private LocalDateTime createdTime;

    @Column(name = "updated_time")
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @Column(name="created_by")
    private Long createdBy;

    @Column(name="updated_by")
    private Long updatedBy;


}
