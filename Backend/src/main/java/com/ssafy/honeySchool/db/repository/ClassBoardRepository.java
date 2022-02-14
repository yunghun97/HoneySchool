package com.ssafy.honeySchool.db.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.honeySchool.db.entity.ClassBoard;
import com.ssafy.honeySchool.db.entity.User;

@Repository
public interface ClassBoardRepository extends JpaRepository<ClassBoard, Integer>{
	// 페이지네이션
//	Page<Post> findByUserOrderByIdDesc(User user, Pageable pageable);
	Page<ClassBoard> findBySchoolAndGradeAndClassesOrderByIdDesc(
			String school, int grade, int classes, Pageable pageable);
	Page<ClassBoard> findBySchoolAndGradeAndClassesAndCategoryOrderByIdDesc(
			String school, int grade, int classes, String category, Pageable pageable);
	Page<ClassBoard> findBySchoolAndGradeAndClassesAndCategoryAndUserOrderByIdDesc(
			String school, int grade, int classes, String category, User user, Pageable pageable);
	
	
	
//	// 전체 검색 (pk 오름차순)
//	List<ClassBoard> findBySchoolAndGradeAndClasses(String school, int grade, int classe);
//	// 전체 검색 (pk 내림차순)
//	List<ClassBoard> findBySchoolAndGradeAndClassesOrderByIdDesc(String school, int grade, int classes);
//	// 카테고리 검색  (pk 내림차순)
//	List<ClassBoard> findBySchoolAndGradeAndClassesAndCategoryOrderByIdDesc(String school, int grade, int classes, String category);
//	// 특정 게시판에서 특정 user가 쓴 글 모아보기 (pk 내림차순)
//	List<ClassBoard> findByCategoryAndSchoolAndGradeAndClassesAndUserOrderByIdDesc(String category, String school, int grade, int classes, User user);
	// 전체게시판 글 상세 검색, 글 삭제
	ClassBoard findBySchoolAndGradeAndClassesAndId(String school, int grade, int classes, int id);
	// 조회수 증가
	@Modifying
	@Query("update ClassBoard p set p.viewcount = p.viewcount + 1 where p.id = :id") 
	int updateView(@Param("id") int id);
	// 전체게시판 게시물 댓글 생성
	ClassBoard findById(int id);
}