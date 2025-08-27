package com.trackingservices.dto;

import com.trackingservices.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackingDto implements Serializable {
    private  Long id;
    private User recruiter;
    private User user;
}
