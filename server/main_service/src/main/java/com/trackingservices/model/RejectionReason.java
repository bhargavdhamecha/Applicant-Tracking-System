package com.trackingservices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Transactional
@Builder
@Table(name="rejection_reason")
public class RejectionReason implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rejectionId")
    private Long rejectionId;

    @Column(name = "reason")
    private String reason;

    @Column(name="user_id")
    private Long userId;

}

