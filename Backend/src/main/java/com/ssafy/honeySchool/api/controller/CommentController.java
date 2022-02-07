package com.ssafy.honeySchool.api.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.ssafy.honeySchool.db.entity.ClassBoard;
import com.ssafy.honeySchool.db.entity.ClassComment;
import com.ssafy.honeySchool.db.repository.ClassBoardRepository;
import com.ssafy.honeySchool.db.repository.ClassCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080/")
@RestController
@RequestMapping("/api/v1/board")
public class CommentController {
	
	@Autowired
	ClassBoardRepository classBoardRepository;
	
	@Autowired
	ClassCommentRepository classCommentRepository;
	
	// 반 게시판 게시물의 댓글 가져오기
	@GetMapping("/class/comment")
    public ResponseEntity<List<ClassComment>> selectClassBoardComments(HttpServletRequest req){
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		int boardId = Integer.parseInt(req.getParameter("board_id"));
		ClassBoard classBoard = classBoardRepository.findBySchoolAndGradeAndClassesAndId(school, grade, classes, boardId);
		List<ClassComment> classComments = classCommentRepository.findClassCommentByClassBoard(classBoard);
		return new ResponseEntity<List<ClassComment>>(classComments, HttpStatus.OK);
    }
	// 반 게시판 게시물에 댓글 쓰기
	@PostMapping("/class/{boardId}/comment/")
    public HttpStatus createComment(@PathVariable int boardId, @RequestBody ClassComment body){
        ClassBoard classBoard = classBoardRepository.findById(boardId);
//        body.setClassBoard(classBoard);
        classCommentRepository.save(ClassComment.builder()
        		.content(body.getContent())
				.writer(body.getWriter())
				.file_link(body.getFile_link())
				.classBoard(classBoard)
				.build());
        return HttpStatus.OK;
    }
	// 댓글 삭제
	// boardId를 아무거나 적어도 comment 삭제가 됨.
	@DeleteMapping("/class/{boardId}/comment/{commentId}")
    public HttpStatus deleteComment(@PathVariable int boardId, @PathVariable int commentId){
		classCommentRepository.deleteById(commentId);
        return HttpStatus.OK;
    }
	// 댓글 수정
	// 댓글이 써진 게시물을 바꾸면 안됨
	@Transactional
	@PatchMapping("/class/{boardId}/comment/{commentId}")
    public ResponseEntity<ClassComment> updateComment(@PathVariable int boardId, @PathVariable int commentId, @RequestBody ClassComment body){
//		ClassBoard classBoard = classBoardRepository.findById(boardId);
//		body.setClassBoard(classBoard);
		ClassComment newComment = classCommentRepository.findById(commentId).get();
		System.out.println(newComment.getContent());		
		System.out.println(newComment);		
		String content = body.getContent();
		String writer = body.getWriter();
		String file_link = body.getFile_link();
        newComment.update(content, writer, file_link);
        System.out.println(newComment.getContent());
        System.out.println(newComment);		
        return new ResponseEntity<ClassComment>(newComment, HttpStatus.OK);
    }
}
