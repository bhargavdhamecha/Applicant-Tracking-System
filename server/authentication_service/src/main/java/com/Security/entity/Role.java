package com.Security.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(allocationSize = 1,sequenceName = "role_seq", name = "role_seq")
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String rolename;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "updated_at")
    private LocalDate updatedAt;
    @Column(name = "updated_by")
    private Integer updatedBy;
    @Column(name = "created_by")
    private Integer createdBy;


    @OneToMany(targetEntity = Permission.class,cascade = CascadeType.DETACH)
    private List<Permission> permissions;

}
