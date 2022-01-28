package com.ssafy.honeySchool.db.repository;

import com.ssafy.honeySchool.db.entity.ClassBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassBoardRepository extends JpaRepository<ClassBoard, Integer>{
	// 전체 검색
	List<ClassBoard> findBySchoolAndGradeAndClasses(String school, int grade, int classe);
	// 카테고리 검색
	List<ClassBoard> findBySchoolAndGradeAndClassesAndCategory(String school, int grade, int classes, String category);
	// 전체게시판 글 상세 검색
	ClassBoard findBySchoolAndGradeAndClassesAndId(String school, int grade, int classes, int id);
	// 조회수 증가
	@Modifying
	@Query("update ClassBoard p set p.viewcount = p.viewcount + 1 where p.id = :id") 
	int updateView(@Param("id") int id);
	
}
