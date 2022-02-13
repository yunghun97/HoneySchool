package com.ssafy.honeySchool.api.controller;

import java.io.File;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.honeySchool.api.dto.ClassBoardDto;
import com.ssafy.honeySchool.api.dto.CommentDto;
import com.ssafy.honeySchool.api.dto.NoticeDto;
import com.ssafy.honeySchool.api.service.BoardService;
import com.ssafy.honeySchool.api.service.NoticeService;
import com.ssafy.honeySchool.db.entity.ClassBoard;
import com.ssafy.honeySchool.db.entity.ClassBoardFile;
import com.ssafy.honeySchool.db.entity.Comment;
import com.ssafy.honeySchool.db.entity.DeleteYn;
import com.ssafy.honeySchool.db.entity.Notice;
import com.ssafy.honeySchool.db.entity.User;
import com.ssafy.honeySchool.db.repository.ClassBoardFileRepository;
import com.ssafy.honeySchool.db.repository.ClassBoardRepository;
import com.ssafy.honeySchool.db.repository.CommentRepository;
import com.ssafy.honeySchool.db.repository.NoticeFileRepository;
import com.ssafy.honeySchool.db.repository.NoticeRepository;
import com.ssafy.honeySchool.db.repository.UserRepository;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/board")
public class NoticeController {
		
	@Autowired
	private NoticeRepository noticeRepository;

	@Autowired
	private NoticeFileRepository noticeFileRepository;
	
	// 서버 상대경로 얻을 때 사용
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private NoticeService noticeService;
	
	// 공지사항 전체 목록
	@GetMapping("/notice")
	public ResponseEntity<?> selectNotice() throws SQLException{
		List<Notice> notices = noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
		// dto로 묶기
		List<NoticeDto> noticeDtos = new ArrayList<NoticeDto>();
		for(int i = 0; i < notices.size(); i++) {
			noticeDtos.add(NoticeDto.from(notices.get(i)));
		}				
		return new ResponseEntity<List<NoticeDto>>(noticeDtos, HttpStatus.OK);
	}
	// 
	@PostMapping("/notice")
	public ResponseEntity<?> createNotice(
			HttpServletRequest req,
			@RequestPart(value="files", required = false) List<MultipartFile> files
	) throws Exception{
		// 상대경로 찾기
		String rootPath = request.getSession().getServletContext().getRealPath("/uploads");
		// 저장 경로 변경 부분
		String resourcesPath = rootPath.substring(0, rootPath.length()-14) + "resources\\static\\uploads";	
		rootPath = resourcesPath;
		
		// User 수정 함
        User user = userRepository.findByUserId(req.getParameter("userId")).get();
               
        // notice 저장
        Notice notice = noticeRepository.save(Notice.builder()
				.title(req.getParameter("title"))
				.content(req.getParameter("content"))
				.user(user)
				.viewcount(0)
				.build());
        // 파일 저장
        Notice sameNotice = noticeService.addNotice(notice, files, rootPath);

		URI uriLocation = new URI("/board/" + notice.getId());
        return ResponseEntity.created(uriLocation).body("{}");	
	}
//	// 전체게시판 글 상세 (+ 댓글 같이 가져옴)
//	@Transactional
//	@GetMapping("/class/detail")
//	public ResponseEntity<Map<String, Object>> detailBoard(HttpServletRequest req) {
//		String school = req.getParameter("school");		
//		int grade = Integer.parseInt(req.getParameter("grade"));
//		int classes = Integer.parseInt(req.getParameter("classes"));
//		int id = Integer.parseInt(req.getParameter("id"));
//		classBoardRepository.updateView(id);
//		// 글정보, 댓글, 파일정보 같이 묶어서 응답
//		ClassBoard detail = classBoardRepository.findBySchoolAndGradeAndClassesAndId(school, grade, classes, id);
//		// ClassBoard dto로 바꾸기
//		ClassBoardDto detailDto = ClassBoardDto.from(detail);
//		
//		// question 파라미터 있는지 확인
//		List<Comment> comments = new ArrayList<Comment>(); 
//        if (req.getParameterMap().containsKey("userId")) {  // 질문게시판
//        	User user = userRepository.findByUserId(req.getParameter("userId")).get();
//        	comments = commentRepository.findCommentByClassBoardAndUserAndParentId(detail, user, 0).get();    		        	
//    	} else {  // 다른 게시판
//    		comments = commentRepository.findCommentByClassBoardOrderByParentIdAsc(detail).get();    		
//    	}
//		
//		
//		// 댓글 dto로 바꾸기
//		List<CommentDto> commentdtos = new ArrayList<CommentDto>();
//		for(int i = 0; i < comments.size(); i++) {
//			commentdtos.add(CommentDto.from(comments.get(i)));
//		}		
//		List<ClassBoardFile> files = classBoardFileRepository.findByBoardIdAndIsDeleted(id, DeleteYn.N);
//		// Map 사용해서 묶기
//		Map<String, Object> map = new HashMap<>();
//		map.put("board", detailDto);
//		map.put("comments", commentdtos);
//		map.put("files", files);
//		return new ResponseEntity<>(map, HttpStatus.OK);
////		return new ResponseEntity<ClassBoard>(detail, HttpStatus.OK);
//	}
	// 공지사항 삭제 - 첨부파일도 같이 지워짐
	@DeleteMapping("/notice/{noticeId}")
	public HttpStatus deleteBoard(@PathVariable int noticeId) {
		// 첨부파일 같이 지우기
		noticeFileRepository.deleteFile(noticeId);
			
		Notice notice = noticeRepository.findById(noticeId);
		noticeRepository.delete(notice);
		return HttpStatus.OK;
	}
//	// 전체게시판 글 수정
//	// API 명세서랑 다르게 일단 entity 자체를 리턴했습니다
//	// Request로 첨부파일 수정 여부 주시면 됩니다 (여기서는 String 'Y', 'N'으로 써놨어요)
//	@Transactional
//	@PutMapping("/class")
//	public ResponseEntity<?> updateBoard(
//			ClassBoard body, 
//			HttpServletRequest req, 
//			@RequestPart(value="files", required = false) List<MultipartFile> files) 
//	throws Exception{
//		String school = body.getSchool();		
//		int grade = body.getGrade();
//		int classes = body.getClasses();
//		int id = body.getId();
//		ClassBoard board = classBoardRepository.findBySchoolAndGradeAndClassesAndId(school, grade, classes, id);
//		
//		// 첨부파일 수정 여부
//		String fileIsChanged = req.getParameter("fileIsChanged");
//		if (fileIsChanged.equals("Y")) {
//			// 기존에 존재하던 첨부파일 모두 삭제 (댓글 빼고 글만)
//			classBoardFileRepository.deleteBoardFile(id);
//			// 현재 추가하는 첨부파일 저장
//			String rootPath = request.getSession().getServletContext().getRealPath("/uploads");
//			String resourcesPath = rootPath.substring(0, rootPath.length()-14) + "resources\\static\\uploads";	
//			rootPath = resourcesPath;
//			ClassBoard sameBoard = boardService.addBoard(board, files, rootPath, 0);
//			System.out.println("첨부파일 저장 됐어");
//		}
//		// 수정 내용 저장
//		String category = body.getCategory();
//		String title = body.getTitle();
//		String content = body.getContent();
//		board.update(category, title, content);
//		// Dto로 변경
//		ClassBoardDto classBoardDto = ClassBoardDto.from(board);
//		System.out.println("글 수정 됐어");
//		return new ResponseEntity<ClassBoardDto>(classBoardDto, HttpStatus.OK);
//	}
}
