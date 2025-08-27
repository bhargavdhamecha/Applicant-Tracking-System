package com.trackingservices.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
public class AllApplicantDataDto implements Serializable {
    private Long id;

    private Long uid;
    private Long trackingId;
    private String firstname;
    private String lastname;
    private String status;
    private String stage;
    private String stream;
    private String round;
    public AllApplicantDataDto(Long id,Long uid,Long trackingId, String firstname,String lastname, String status, String stage, String stream, String round) {
        this.id = id;
        this.uid=uid;
        this.trackingId = trackingId;
        this.firstname = firstname;
        this.lastname=lastname;
        this.status = status;
        this.stage = stage;
        this.stream = stream;
        this.round = round;
    }

}
