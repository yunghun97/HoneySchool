package com.ssafy.honeySchool.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "notice_id")
    private int noticeId;

//    @NotEmpty
    @NotNull
    private String original_file_name;
//    @NotEmpty
    @NotNull
    private String stored_file_path;

    private long file_size;
    
    @Enumerated(value = EnumType.STRING)
    @Column(name = "delete_yn")
    private DeleteYn isDeleted;
}
