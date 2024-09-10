package com.accepted.givutake.global.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name="Region")
@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Region extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_idx", nullable = false)
    private int regionIdx;

    @Column(name = "region_name", nullable = false)
    private String regionName;
}