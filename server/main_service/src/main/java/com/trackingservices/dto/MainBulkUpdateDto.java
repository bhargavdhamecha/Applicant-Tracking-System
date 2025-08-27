package com.trackingservices.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MainBulkUpdateDto implements Serializable {
    private BulkUpdateData data;
    private List<BulkUpdateId> ids;
}
