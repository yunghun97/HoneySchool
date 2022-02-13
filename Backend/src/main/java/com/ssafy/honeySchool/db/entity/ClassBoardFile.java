package com.ssafy.honeySchool.db.entity;

import javax.persistence.Column;
//import javax.validation.constraints.NotEmpty;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassBoardFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "board_id")
    private int boardId;
    @Column(name = "comment_id")
    private int commentId;

    // 원본 파일이름 과 서버에 저장된 파일 경로 를 분리한 이유?
    // 동일한 이름을 가진 파일이 업로드가 된다면 오류가 생긴다.
    // 이를 해결하기 위함
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