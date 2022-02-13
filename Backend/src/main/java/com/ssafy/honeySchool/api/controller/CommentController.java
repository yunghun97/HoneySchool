package com.ssafy.honeySchool.api.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.honeySchool.api.dto.CommentDto;
import com.ssafy.honeySchool.api.service.BoardService;
import com.ssafy.honeySchool.db.entity.ClassBoard;
import com.ssafy.honeySchool.db.entity.ClassBoardFile;
import com.ssafy.honeySchool.db.entity.Comment;
import com.ssafy.honeySchool.db.entity.DeleteYn;
import com.ssafy.honeySchool.db.entity.User;
import com.ssafy.honeySchool.db.repository.ClassBoardFileRepository;
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
	
	@Autowired
    private BoardService boardService;
	
	@Autowired
	ClassBoardFileRepository classBoardFileRepository;
	
	// 서버 상대경로 얻을 때 사용
	@Autowired
	private HttpServletRequest request;
	
	// 반 게시판 게시물에 댓글, 대댓글 쓰기
	@PostMapping("/class/{boardId}/comment/")
    public HttpStatus createComment(
    		@PathVariable int boardId, 
    		HttpServletRequest req, 
    		@RequestPart(value="files", required = false) List<MultipartFile> files
	) throws Exception {
        ClassBoard classBoard = classBoardRepository.findById(boardId);
        User user = userRepository.findByUserId(req.getParameter("userId")).get();
        // parent_id 파라미터 있는지 확인
        Comment comment;
        if (req.getParameterMap().containsKey("parent_id")) {  // 대댓글 쓰기
        	int parentId = Integer.parseInt(req.getParameter("parent_id"));
        	
        	Comment parentComment = commentRepository.findById(parentId).get();
        	// parent 댓글과 같은 게시물인지 확인
        	if (parentComment.getClassBoard().getId() != classBoard.getId()) {
        		return HttpStatus.BAD_REQUEST;
        	}
        	// 대댓글 및에 댓글 쓰지 못하도록 막기
        	if (parentComment.getParentId() == 0) {
        		comment = commentRepository.save(Comment.builder()
        				.content(req.getParameter("content"))
        				.classBoard(classBoard)
        				.parentId(parentId)
        				.user(user)
        				.build());       	     		
        	} else {
        		return HttpStatus.BAD_REQUEST;
        	}
    	} else { 	// 댓글 쓰기
    		comment = commentRepository.save(Comment.builder()
    				.content(req.getParameter("content"))
    				.classBoard(classBoard)
//    				.parent_id(Integer.parseInt(req.getParameter("parent_id")))
    				.user(user)
    				.build());  	
    		// 첨부파일 저장 (boardController 참고)
    		// 상대경로 얻기
    		String rootPath = request.getSession().getServletContext().getRealPath("/uploads");
    		System.out.println("바뀐 static path?? : " + rootPath);
    		String resourcesPath = rootPath.substring(0, rootPath.length()-14) + "resources\\static\\uploads";	
    		rootPath = resourcesPath;
    		// 파일 저장
    		ClassBoard sameBoard = boardService.addBoard(classBoard, files, rootPath, comment.getId());    		
    	}

        return HttpStatus.OK;
    }
	
	// 댓글, 대댓글 삭제
	// boardId 정확하게 적어야 file 삭제 같이 됨.
	// 댓글 삭제하면 파일도 같이 삭제 (파일 없는 댓글도 잘 삭제)
	@Transactional
	@DeleteMapping("/class/{boardId}/comment/{commentId}")
    public HttpStatus deleteComment(@PathVariable int boardId, @PathVariable int commentId){
		
		commentRepository.deleteById(commentId);
		commentRepository.deleteAllByParentId(commentId);
		// 파일 삭제
		classBoardFileRepository.deleteCommentFile(boardId, commentId);
		
        return HttpStatus.OK;
    }
	
	// 댓글 조회
	// 댓글 수정이나 삭제할 때 사용
	@GetMapping("/class/{boardId}/comment")
    public ResponseEntity<?> selectClassBoardComments(@PathVariable int boardId){
		ClassBoard classBoard = classBoardRepository.findById(boardId);
		List<Comment> comments = commentRepository.findCommentByClassBoardOrderByParentIdAsc(classBoard).get();
		List<ClassBoardFile> files = classBoardFileRepository.selectCommentFiles(boardId);
		Map<String, Object> map = new HashMap<>();
		map.put("comments", comments);
		map.put("files", files);
		return new ResponseEntity<Map>(map, HttpStatus.OK);
    }
	
	// 댓글 수정
	// fileIsChanged 파라미터 없이, 수정하면 무조건 첨부파일 갈아치움
	@Transactional
	@PutMapping("/class/{boardId}/comment/{commentId}")
    public ResponseEntity<?> updateComment(
    		@PathVariable int boardId, 
    		@PathVariable int commentId, 
    		HttpServletRequest req,
    		@RequestPart(value="files", required = false) List<MultipartFile> files
	) throws Exception{
		Comment newComment = commentRepository.findById(commentId).get();
		String content = req.getParameter("content");
        newComment.update(content);
        if (newComment.getParentId() == 0) {  // 댓글일 때
        	// 기존에 존재하던 첨부파일 모두 삭제
        	classBoardFileRepository.deleteCommentFile(boardId, commentId);
        	ClassBoard classBoard = classBoardRepository.findById(boardId);
        	// 댓글인데 files를 안받은 경우
//        	if (files.isEmpty() == false) {
    		// 현재 추가하는 첨부파일 저장 (files parameter가 아예 안오더라도 오류 안남)
    		// 상대경로 얻기
    		String rootPath = request.getSession().getServletContext().getRealPath("/uploads");
    		System.out.println("바뀐 static path?? : " + rootPath);
    		String resourcesPath = rootPath.substring(0, rootPath.length()-14) + "resources\\static\\uploads";	
    		rootPath = resourcesPath;
    		// 파일 저장
    		ClassBoard sameBoard = boardService.addBoard(classBoard, files, rootPath, newComment.getId());    		        	        		
//        	}
        }
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
