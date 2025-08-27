package com.trackingservices.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
public class BulkUpdateData implements Serializable {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss",iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
    private Boolean futureRef;
    private String review;
    private String status;

}
