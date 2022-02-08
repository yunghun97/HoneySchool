package com.ssafy.honeySchool.db.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ssafy.honeySchool.db.entity.ClassBoardFile;
import com.ssafy.honeySchool.db.entity.DeleteYn;

public interface ClassBoardFileRepository extends CrudRepository<ClassBoardFile, Integer> {
	ClassBoardFile save(ClassBoardFile classBoardFile);

    List<ClassBoardFile> findByBoardIdAndIsDeleted(int board_id, DeleteYn isDeleted);

    // 해당 게시물의 파일 삭제 (deleteYn 컬럼을 Y로 바꾼다. 반대는 불가능)
    @Modifying // select 문이 아님을 나타낸다
    @Query(value = "UPDATE class_board_file m set m.delete_yn = 'Y' where m.board_id = :board_id and m.delete_yn != 'Y'", nativeQuery = true)
    void deleteFile(@Param("board_id")int board_id);
//    void changePwd(@Param("password")String password) throws Exception;
}