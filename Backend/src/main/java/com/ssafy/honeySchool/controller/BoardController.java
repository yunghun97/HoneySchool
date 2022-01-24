package com.ssafy.honeySchool.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.honeySchool.Service.BoardService;
import com.ssafy.honeySchool.model.BoardDto;

@CrossOrigin(origins = "http://localhost:8080/")
@RestController
@RequestMapping("/api/v1/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/class")
	public ResponseEntity<List<BoardDto>> selectBoard(HttpServletRequest req) throws SQLException{
		System.out.println("컨트롤러 진입");
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		return new ResponseEntity<List<BoardDto>>(boardService.selectBoard(school, grade, classes),HttpStatus.OK);
	}
	@PostMapping("/class")
	public HttpStatus insertBoard(BoardDto boardDto) throws SQLException{
		boardService.insertBoard(boardDto);
		return HttpStatus.OK;
	}
	@GetMapping("/test")
	public int test2() {
		return 5;
	}
}
