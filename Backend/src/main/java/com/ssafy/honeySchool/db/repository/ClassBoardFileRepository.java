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
    // 댓글 조회할 때
    @Query(value = "SELECT * FROM class_board_file m where m.board_id = :board_id and m.delete_yn != 'Y' and comment_id != 0", nativeQuery = true)
    List<ClassBoardFile> selectCommentFiles(@Param("board_id")int board_id);

    // 게시물 삭제할 때, 게시물과 댓글의 파일 모두 삭제 (deleteYn 컬럼을 Y로 바꾼다. 반대는 불가능)
    @Modifying // select 문이 아님을 나타낸다
    @Query(value = "UPDATE class_board_file m set m.delete_yn = 'Y' where m.board_id = :board_id and m.delete_yn != 'Y'", nativeQuery = true)
    void deleteFile(@Param("board_id")int board_id);
    // 게시물 수정할 때, 게시물의 파일만 모두 삭제 (deleteYn 컬럼을 Y로 바꾼다. 반대는 불가능)
    @Modifying // select 문이 아님을 나타낸다
    @Query(value = "UPDATE class_board_file m set m.delete_yn = 'Y' where m.board_id = :board_id and m.delete_yn != 'Y' and m.comment_id = 0", nativeQuery = true)
    void deleteBoardFile(@Param("board_id")int board_id);
    // 해당 댓글의 파일 삭제 (deleteYn 컬럼을 Y로 바꾼다. 반대는 불가능)
    @Modifying // select 문이 아님을 나타낸다
    @Query(value = "UPDATE class_board_file m set m.delete_yn = 'Y' where m.board_id = :board_id and m.delete_yn != 'Y' and m.comment_id = :comment_id", nativeQuery = true)
    void deleteCommentFile(@Param("board_id")int board_id, @Param("comment_id")int comment_id);
//    void changePwd(@Param("password")String password) throws Exception;
}