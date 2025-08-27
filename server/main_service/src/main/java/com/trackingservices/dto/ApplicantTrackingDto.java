package com.trackingservices.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class ApplicantTrackingDto implements Serializable {
    private String status;
    private String stage;
    private String review;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String round;

    public ApplicantTrackingDto(String status, String stage, String review, LocalDateTime startDate, LocalDateTime endDate, String round) {
        this.status = status;
        this.stage = stage;
        this.review = review;
        this.startDate = startDate;
        this.endDate = endDate;
        this.round = round;
    }
}
