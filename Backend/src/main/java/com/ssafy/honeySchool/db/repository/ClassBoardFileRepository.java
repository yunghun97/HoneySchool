package com.ssafy.honeySchool.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ssafy.honeySchool.db.entity.ClassBoardFile;
import com.ssafy.honeySchool.db.entity.DeleteYn;

public interface ClassBoardFileRepository extends CrudRepository<ClassBoardFile, Integer> {
	ClassBoardFile save(ClassBoardFile classBoardFile);

    List<ClassBoardFile> findByBoardIdAndIsDeleted(int board_id, DeleteYn isDeleted);
}