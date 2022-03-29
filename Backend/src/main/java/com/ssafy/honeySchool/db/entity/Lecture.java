package com.ssafy.honeySchool.db.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lecture extends BaseEntity{
    private String title;
    private String user_id;
    private String teacher;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String thumbnail_url;


}
