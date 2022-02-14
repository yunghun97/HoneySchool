package com.ssafy.honeySchool.db.entity;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class School extends BaseEntity{
    private String school;
}
