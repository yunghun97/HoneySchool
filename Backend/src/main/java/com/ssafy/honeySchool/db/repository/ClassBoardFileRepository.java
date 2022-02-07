package com.ssafy.honeySchool.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ssafy.db.entity.ClassBoardFile;

public interface ClassBoardFileRepository extends CrudRepository<ClassBoardFile, Integer> {
	ClassBoardFile save(ClassBoardFile classBoardFile);

    // file 테이블 boardIdx 컬럼 이름을 board_id 로 바꿨더니 에러가 난다. 일단 주석처리
//    List<BoardPicture> findAllByBoardId(int board_id);
}