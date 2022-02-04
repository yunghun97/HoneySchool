package com.ssafy.honeySchool.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.honeySchool.db.entity.ClassBoard;
import com.ssafy.honeySchool.db.entity.ClassComment;
import com.ssafy.honeySchool.db.repository.ClassBoardRepository;
import com.ssafy.honeySchool.db.repository.ClassCommentRepository;



@CrossOrigin(origins = "http://localhost:8080/")
@RestController
@RequestMapping("/api/v1/board/class")
public class CommentController {
	
	@Autowired
	ClassBoardRepository classBoardRepository;
	
	@Autowired
	ClassCommentRepository classCommentRepository;
	
	// 반 게시판 게시물의 댓글 가져오기
	@GetMapping("/comment")
    public ResponseEntity<List<ClassComment>> selectClassBoardComments(HttpServletRequest req){
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		int id = Integer.parseInt(req.getParameter("board_id"));
		ClassBoard classBoard = classBoardRepository.findBySchoolAndGradeAndClassesAndId(school, grade, classes, id);
		List<ClassComment> classComments = classCommentRepository.findClassCommentByClassBoard(classBoard);
		return new ResponseEntity<List<ClassComment>>(classComments, HttpStatus.OK);
    }
	// 반 게시판 게시물에 댓글 쓰기
	@PostMapping("/comment")
    public HttpStatus createComment(ClassComment body){
		classCommentRepository.save(ClassComment.builder()
				.board_id(body.getBoard_id())
				.content(body.getContent())
				.writer(body.getWriter())
				.file_link(body.getFile_link())
				.build());
        return HttpStatus.OK;
    }
	
}
