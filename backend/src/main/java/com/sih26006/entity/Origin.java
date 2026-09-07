package com.sih26006.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "origins")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Origin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "origin_id")
    private Integer originId;

    @Column(unique = true, nullable = false)
    private String originName;

    private String country;

    private String region;

    private String description;

    @Column(nullable = false)
    private Boolean isActive = true;
}
