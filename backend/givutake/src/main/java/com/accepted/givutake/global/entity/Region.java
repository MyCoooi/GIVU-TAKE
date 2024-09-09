package com.accepted.givutake.global.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Table(name="Region")
@Builder
@AllArgsConstructor
public class Region extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_idx", nullable = false)
    private int regionIdx;

    @Column(name = "region_name", nullable = false)
    private String regionName;
}