package com.ssafy.honeySchool.controller;

import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.db.entity.ClassBoard;
import com.ssafy.db.repository.ClassBoardRepository;
@CrossOrigin(origins = "http://localhost:8080/")
@RestController
@RequestMapping("/api/v1/board")
public class BoardController {
		
	@Autowired
	private ClassBoardRepository classBoardRepository;
	
	// 전체 검색
	@GetMapping("/class")
	public ResponseEntity<List<ClassBoard>> selectBoard(HttpServletRequest req) throws SQLException{
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		return new ResponseEntity<List<ClassBoard>>(classBoardRepository.findBySchoolAndGradeAndClasses(school, grade, classes),HttpStatus.OK);
	}
	@PostMapping("/class")
	public HttpStatus insertBoard(ClassBoard body) throws SQLException{
		System.out.println("insert 집입");				
		// 데이터 저장하기
		classBoardRepository.save(ClassBoard.builder()
				.category(body.getCategory())
				.title(body.getTitle())
				.content(body.getContent())
				.writer(body.getWriter())
				.school(body.getSchool())
				.grade(body.getGrade())
				.classes(body.getClasses())
				.file_link(body.getFile_link())
				.viewcount(0)
				.build());				
		return HttpStatus.OK;
	}
	// Jpa로 category 구분해서 가져오기
	@GetMapping("/class/category")
	public ResponseEntity<List<ClassBoard>> selectCategory(HttpServletRequest req) {
		String school = req.getParameter("school");		
		String category = req.getParameter("category");
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));	
		
		return new ResponseEntity<List<ClassBoard>>(classBoardRepository.findBySchoolAndGradeAndClassesAndCategory(school, grade, classes, category),HttpStatus.OK);
	}	
	// 전체게시판 글 상세
	@Transactional
	@GetMapping("/class/detail")
	public ResponseEntity<ClassBoard> detailBoard(HttpServletRequest req) {
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		int id = Integer.parseInt(req.getParameter("id"));
		classBoardRepository.updateView(id);
		ClassBoard detail = classBoardRepository.findBySchoolAndGradeAndClassesAndId(school, grade, classes, id);
		return new ResponseEntity<ClassBoard>(detail, HttpStatus.OK);
	}
	// 전체게시판 글 삭제
	@DeleteMapping("/class")
	public HttpStatus deleteBoard(HttpServletRequest req) {
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		int id = Integer.parseInt(req.getParameter("id"));
		ClassBoard board = classBoardRepository.findBySchoolAndGradeAndClassesAndId(school, grade, classes, id);
		classBoardRepository.delete(board);
		return HttpStatus.OK;
	}
	// 전체게시판 글 수정
	// API 명세서랑 다르게 일단 entity 자체를 리턴했습니다
	@Transactional
	@PatchMapping("/class")
	public ResponseEntity<ClassBoard> updateBoard(ClassBoard body) throws SQLException{
		String school = body.getSchool();		
		int grade = body.getGrade();
		int classes = body.getClasses();
		int id = body.getId();
		ClassBoard board = classBoardRepository.findBySchoolAndGradeAndClassesAndId(school, grade, classes, id);
		String category = body.getCategory();
		String title = body.getTitle();
		String content = body.getContent();
		String file_link = body.getFile_link();
		board.update(category, title, content, file_link);		
		return new ResponseEntity<ClassBoard>(board, HttpStatus.OK);
	}
}
