package com.trackingservices.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mainTable")
@Builder
public class ApplicantTracking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "stage", updatable = false)
    private String stage;

    @Column(name = "startDate",updatable = false)
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss",iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @Column(updatable = false)
    private String round;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    Boolean futureRef;

    @Column(name = "endDate")
    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss",iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
    @Column(name = "review")
    private String review;
    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.EAGER,targetEntity = Tracking.class)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "trackingID", referencedColumnName ="tid")
    private Tracking tracking;
}
