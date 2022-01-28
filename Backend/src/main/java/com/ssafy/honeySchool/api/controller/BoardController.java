package com.ssafy.honeySchool.api.controller;


import com.ssafy.honeySchool.db.entity.ClassBoard;
import com.ssafy.honeySchool.db.repository.ClassBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080/")
@RestController
@RequestMapping("/api/v1/board")
public class BoardController {
		
	@Autowired
	private ClassBoardRepository classBoardRepository;
	
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
	
}
