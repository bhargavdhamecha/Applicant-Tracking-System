package com.trackingservices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

@Entity
@Table(name = "stream")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stream {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "stream_id")
    private Long streamId;

    @Column(name="stream_name")
    private String streamName;

    @Column(name = "created_time")
    @CreationTimestamp
    private LocalDateTime createdTime;

    @Column(name="updated_time")
    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean isActive;
}