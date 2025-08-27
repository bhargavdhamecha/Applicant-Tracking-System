package com.trackingservices.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String rolename;
    @Column(name = "created_at",updatable = false)
    @CreationTimestamp
    @JsonIgnore
    private LocalDate createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    @JsonIgnore
    private LocalDate updatedAt;
    @Column(name = "updated_by")
    private Long updatedBy;
    @Column(name = "created_by")
    private Long createdBy;
    @OneToMany(targetEntity = Permission.class,cascade = CascadeType.DETACH)
    private List<Permission> permissions;

}
