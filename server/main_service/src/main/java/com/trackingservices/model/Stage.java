package com.trackingservices.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "stage")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stage_id")
    private Long stageId;

    @Column(name = "stage_name")
    @NotNull
    private String stageName;

    @Column(name = "created_time")
    @UpdateTimestamp
    private LocalDateTime createdTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean isActive;

    @Column(name = "sequence_no")
    private int sequenceNo;

    @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Round> rounds;

    @PrePersist
    private void prePersist() {
        if (this.stageId != null) {
            long stageIdValue = this.stageId.longValue();
            this.sequenceNo = (int) stageIdValue;
        }
    }

    @Override
    public String toString() {
        return "Stage{" +
                "stageId=" + stageId +
                ", stageName='" + stageName + '\'' +
                ", createdTime=" + createdTime +
                ", createdBy='" + createdBy + '\'' +
                ", isActive=" + isActive +
                ", sequenceNo=" + sequenceNo +
                ", rounds=" + rounds +
                '}';
    }

}
