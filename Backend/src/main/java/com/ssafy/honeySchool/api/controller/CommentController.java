package com.ssafy.honeySchool.api.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.honeySchool.api.dto.CommentDto;
import com.ssafy.honeySchool.db.entity.ClassBoard;
import com.ssafy.honeySchool.db.entity.Comment;
import com.ssafy.honeySchool.db.entity.DeleteYn;
import com.ssafy.honeySchool.db.entity.User;
import com.ssafy.honeySchool.db.repository.ClassBoardRepository;
import com.ssafy.honeySchool.db.repository.CommentRepository;
import com.ssafy.honeySchool.db.repository.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/board")
public class CommentController {
	
	@Autowired
	ClassBoardRepository classBoardRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	// 반 게시판 게시물에 댓글, 대댓글 쓰기
	@PostMapping("/class/{boardId}/comment/")
    public HttpStatus createComment(@PathVariable int boardId, HttpServletRequest req) {
        ClassBoard classBoard = classBoardRepository.findById(boardId);
        User user = userRepository.findByUserId(req.getParameter("userId")).get();
        // parent_id 파라미터 있는지 확인
        if (req.getParameterMap().containsKey("parent_id")) { 
        	int parentId = Integer.parseInt(req.getParameter("parent_id"));
        	// 대댓글 및에 댓글 쓰지 못하도록 막기
        	Comment parentComment = commentRepository.findById(parentId).get();
        	if (parentComment.getParentId() == 0) {
        		commentRepository.save(Comment.builder()
        				.content(req.getParameter("content"))
        				.classBoard(classBoard)
        				.parentId(parentId)
        				.user(user)
        				.build());       	     		
        	} else {
        		return HttpStatus.BAD_REQUEST;
        	}
    	} else { 		
    		commentRepository.save(Comment.builder()
    				.content(req.getParameter("content"))
    				.classBoard(classBoard)
//    				.parent_id(Integer.parseInt(req.getParameter("parent_id")))
    				.user(user)
    				.build());  		
    	}
        return HttpStatus.OK;
    }
	
	// 댓글, 대댓글 삭제
	// boardId를 아무거나 적어도 comment 삭제가 됨.
	@Transactional
	@DeleteMapping("/class/{boardId}/comment/{commentId}")
    public HttpStatus deleteComment(@PathVariable int boardId, @PathVariable int commentId){
		
		commentRepository.deleteById(commentId);
		commentRepository.deleteAllByParentId(commentId);
		
        return HttpStatus.OK;
    }
	
	// 사실상 안쓰지만 놔둠 - 게시물 상세에서 다 가져오니까
	@GetMapping("/class/{boardId}/comment")
    public ResponseEntity<?> selectClassBoardComments(@PathVariable int boardId){
		ClassBoard classBoard = classBoardRepository.findById(boardId);
		List<Comment> comments = commentRepository.findCommentByClassBoardOrderByParentIdAsc(classBoard).get();
		return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }
	
	// 댓글 수정
	@Transactional
	@PutMapping("/class/{boardId}/comment/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable int boardId, @PathVariable int commentId, @RequestBody Comment body){
		Comment newComment = commentRepository.findById(commentId).get();
		String content = body.getContent();
        newComment.update(content);
        return new ResponseEntity<Comment>(newComment, HttpStatus.OK);
    }
	
	// 질문게시판 대댓글보기 버튼
	@GetMapping("/class/{boardId}/comment/{commentId}")
    public ResponseEntity<?> selectReply(@PathVariable int boardId, @PathVariable int commentId){
		List<Comment> replys = commentRepository.findCommentByParentId(commentId).get();
		// dto로 묶기
		List<CommentDto> replyDtos = new ArrayList<CommentDto>();
		for(int i = 0; i < replys.size(); i++) {
			replyDtos.add(CommentDto.from(replys.get(i)));
		}		
        return new ResponseEntity<List<CommentDto>>(replyDtos, HttpStatus.OK);
    }
	
	
	
	
// 예전 ClassComment때 코드
//	@Autowired
//	ClassBoardRepository classBoardRepository;
//	
//	@Autowired
//	ClassCommentRepository classCommentRepository;
//	
//	// 반 게시판 게시물의 댓글 가져오기
//	@GetMapping("/class/comment")
//    public ResponseEntity<List<ClassComment>> selectClassBoardComments(HttpServletRequest req){
//		String school = req.getParameter("school");		
//		int grade = Integer.parseInt(req.getParameter("grade"));
//		int classes = Integer.parseInt(req.getParameter("classes"));
//		int boardId = Integer.parseInt(req.getParameter("board_id"));
//		ClassBoard classBoard = classBoardRepository.findBySchoolAndGradeAndClassesAndId(school, grade, classes, boardId);
//		List<ClassComment> classComments = classCommentRepository.findClassCommentByClassBoard(classBoard);
//		return new ResponseEntity<List<ClassComment>>(classComments, HttpStatus.OK);
//    }
//	// 반 게시판 게시물에 댓글 쓰기
//	@PostMapping("/class/{boardId}/comment/")
//    public HttpStatus createComment(@PathVariable int boardId, @RequestBody ClassComment body){
//        ClassBoard classBoard = classBoardRepository.findById(boardId);
////        body.setClassBoard(classBoard);
//        classCommentRepository.save(ClassComment.builder()
//        		.content(body.getContent())
//				.writer(body.getWriter())
//				.file_link(body.getFile_link())
//				.classBoard(classBoard)
//				.build());
//        return HttpStatus.OK;
//    }
//	// 댓글 삭제
//	// boardId를 아무거나 적어도 comment 삭제가 됨.
//	@DeleteMapping("/class/{boardId}/comment/{commentId}")
//    public HttpStatus deleteComment(@PathVariable int boardId, @PathVariable int commentId){
//		classCommentRepository.deleteById(commentId);
//        return HttpStatus.OK;
//    }
//	// 댓글 수정
//	// 댓글이 써진 게시물을 바꾸면 안됨
//	@Transactional
//	@PutMapping("/class/{boardId}/comment/{commentId}")
//    public ResponseEntity<ClassComment> updateComment(@PathVariable int boardId, @PathVariable int commentId, @RequestBody ClassComment body){
////		ClassBoard classBoard = classBoardRepository.findById(boardId);
////		body.setClassBoard(classBoard);
//		ClassComment newComment = classCommentRepository.findById(commentId).get();
//		System.out.println(newComment.getContent());		
//		System.out.println(newComment);		
//		String content = body.getContent();
//		String writer = body.getWriter();
//		String file_link = body.getFile_link();
//        newComment.update(content, writer, file_link);
//        System.out.println(newComment.getContent());
//        System.out.println(newComment);		
//        return new ResponseEntity<ClassComment>(newComment, HttpStatus.OK);
//    }
}
