package com.trackingservices.dto;

import com.trackingservices.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class JobApplicationDetailsDto {


    private String stream;
    private String designation;
    private Integer totalExpYears;
    private Integer relevantExpYears;
    private Boolean isFresher;
    private Long oldCtc;
    private Long expectedCtc;
    private Integer noticePeriod;
    private String roleAppliedFor;
    private Boolean willingToRelocate;
    private String jobDescription;
    private Boolean anyCurrentOffer;
    private Boolean upcomingInterview;
    private String reasonForChange;
    private String referralSource;
    private User user;


}
