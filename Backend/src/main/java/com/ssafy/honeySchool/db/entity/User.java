package com.ssafy.honeySchool.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements Serializable{
    private String name;

    private String position;

    private String school;

    private int grade;

    private int classes;

    private int number;

    @Column(name="user_id")
    private String userId;

    private String email;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private LocalDate birth;
}
