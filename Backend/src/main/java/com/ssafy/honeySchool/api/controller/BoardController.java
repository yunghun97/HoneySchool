package com.ssafy.honeySchool.api.controller;


import java.io.File;
import java.net.URI;
import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.honeySchool.api.service.BoardService;
import com.ssafy.honeySchool.db.entity.ClassBoard;
import com.ssafy.honeySchool.db.entity.ClassBoardFile;
import com.ssafy.honeySchool.db.entity.ClassComment;
import com.ssafy.honeySchool.db.entity.DeleteYn;
import com.ssafy.honeySchool.db.entity.User;
import com.ssafy.honeySchool.db.repository.ClassBoardFileRepository;
import com.ssafy.honeySchool.db.repository.ClassBoardRepository;
import com.ssafy.honeySchool.db.repository.ClassCommentRepository;
import com.ssafy.honeySchool.db.repository.UserRepository;
@CrossOrigin(origins = "http://localhost:8080/")
@RestController
@RequestMapping("/api/v1/board")
public class BoardController {
		
	@Autowired
	private ClassBoardRepository classBoardRepository;
	
	@Autowired
	ClassCommentRepository classCommentRepository;
	
	@Autowired
	ClassBoardFileRepository classBoardFileRepository;
	
	// 서버 상대경로 얻을 때 사용
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
    private BoardService boardService;
	
	// User 객체 못가져와서 임시로 사용
	@Autowired
	UserRepository userRepository;
	
	// 반 게시판 전체 목록
	@GetMapping("/class")
	public ResponseEntity<List<ClassBoard>> selectBoard(HttpServletRequest req) throws SQLException{
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		return new ResponseEntity<List<ClassBoard>>(classBoardRepository.findBySchoolAndGradeAndClassesOrderByIdDesc(school, grade, classes),HttpStatus.OK);
	}
//	@PostMapping("/class")
//	public HttpStatus insertBoard(ClassBoard body) throws SQLException{
//		System.out.println("insert 집입");				
//		// 데이터 저장하기
//		classBoardRepository.save(ClassBoard.builder()
//				.category(body.getCategory())
//				.title(body.getTitle())
//				.content(body.getContent())
//				.writer(body.getWriter())
//				.school(body.getSchool())
//				.grade(body.getGrade())
//				.classes(body.getClasses())
//				.file_link(body.getFile_link())
//				.viewcount(0)
//				.build());				
//		return HttpStatus.OK;
//	}
	// User 객체 못가져와서 임시로 url에 써서 받아옴
	@PostMapping("/class/{userId}")
	public ResponseEntity<?> createBoard(
			@PathVariable String userId,
			ClassBoard body, 
			// validation 라이브러리가 없어서 @Valid를 뺌 
//			@Valid @RequestParam("files") List<MultipartFile> files
//			@RequestParam("files") List<MultipartFile> files
			@RequestPart(value="files", required = false) List<MultipartFile> files
	) throws Exception{
		System.out.println("insert 집입");
		
		// 원래 코드에서는 FileHandler 에서 절대경로를 얻었지만, 상대경로로 바꿔야해서 controller에서 생성 -> service로 전달 -> FileHandler로 전달 -> 파일 저장
		String rootPath = request.getSession().getServletContext().getRealPath("/uploads");
		System.out.println("바뀐 static path?? : " + rootPath);
		// 저장 경로 변경 부분
		String resourcesPath = rootPath.substring(0, rootPath.length()-14) + "resources\\static\\uploads";	
//		System.out.println("자른 거: " + resourcesPath + "resources\\static\\uploads");
		
		rootPath = resourcesPath;
		System.out.println("루트패스 수정: " + rootPath);
		
		// User 객체 못가져와서 임시로 사용 (User랑 합치면 수정)
        User user = userRepository.findByUserId(userId).get();
               
        // 파일 저장과 board 저장을 분리하기
        // board 저장
        ClassBoard board = classBoardRepository.save(ClassBoard.builder()
				.category(body.getCategory())
				.title(body.getTitle())
				.content(body.getContent())
				.user(user)
				.school(body.getSchool())
				.grade(body.getGrade())
				.classes(body.getClasses())
				.viewcount(0)
				.build());
        // 파일 저장
        ClassBoard sameBoard = boardService.addBoard(board, files, rootPath);
        
		// BoardService에서는 전달한 게시글의 id를 못찾는다. -> id를 DB에서 생성하기 때문에, 데이터가 DB에 들어가고 난 후에만 getId가 가능하다. 그전까지는 0으로 되는듯
//		System.out.println("보드 id : " + board.getId());
		URI uriLocation = new URI("/board/" + board.getId());
        return ResponseEntity.created(uriLocation).body("{}");	
	}
	// Jpa로 category 구분해서 가져오기 (pk 내림차순)
	@GetMapping("/class/category")
	public ResponseEntity<List<ClassBoard>> selectCategory(HttpServletRequest req) {
		String category = req.getParameter("category");
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));	
		
		return new ResponseEntity<List<ClassBoard>>(classBoardRepository.findBySchoolAndGradeAndClassesAndCategoryOrderByIdDesc(school, grade, classes, category),HttpStatus.OK);
	}
	// 특정 category에서 특정 user가 쓴 글 모아보기 (pk 내림차순)
	// User 객체 못가져와서 임시로 url에 써서 받아옴
	@GetMapping("/class/category/user/{userId}")
	public ResponseEntity<List<ClassBoard>> selectCategoryAndUser(HttpServletRequest req, @PathVariable String userId) throws SQLException{
		String category = req.getParameter("category");
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		
		// User 객체 못가져와서 임시로 사용 (User랑 합치면 수정)
        User user = userRepository.findByUserId(userId).get();
        
		return new ResponseEntity<List<ClassBoard>>(
				classBoardRepository.findByCategoryAndSchoolAndGradeAndClassesAndUserOrderByIdDesc(category, school, grade, classes, user), 
				HttpStatus.OK);
	}
	// 전체게시판 글 상세 (+ 댓글 같이 가져옴)
	@Transactional
	@GetMapping("/class/detail")
	public ResponseEntity<Map<String, Object>> detailBoard(HttpServletRequest req) {
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		int id = Integer.parseInt(req.getParameter("id"));
		classBoardRepository.updateView(id);
		// 글정보, 댓글, 파일정보 같이 묶어서 응답
		ClassBoard detail = classBoardRepository.findBySchoolAndGradeAndClassesAndId(school, grade, classes, id);
		List<ClassComment> comments = classCommentRepository.findClassCommentByClassBoard(detail);
		List<ClassBoardFile> files = classBoardFileRepository.findByBoardIdAndIsDeleted(id, DeleteYn.N);
		// Map 사용해서 묶기
		Map<String, Object> map = new HashMap<>();
		
		map.put("board", detail);
		map.put("comments", comments);
		map.put("files", files);
		return new ResponseEntity<>(map, HttpStatus.OK);
//		return new ResponseEntity<ClassBoard>(detail, HttpStatus.OK);
	}
	// 전체게시판 글 삭제 - 첨부파일도 같이 지워짐
	@DeleteMapping("/class")
	public HttpStatus deleteBoard(HttpServletRequest req) {
		String school = req.getParameter("school");		
		int grade = Integer.parseInt(req.getParameter("grade"));
		int classes = Integer.parseInt(req.getParameter("classes"));
		int id = Integer.parseInt(req.getParameter("id"));
		// 첨부파일 같이 지우기
		classBoardFileRepository.deleteFile(id);
		
		ClassBoard board = classBoardRepository.findBySchoolAndGradeAndClassesAndId(school, grade, classes, id);
		classBoardRepository.delete(board);
		return HttpStatus.OK;
	}
	// 전체게시판 글 수정
	// API 명세서랑 다르게 일단 entity 자체를 리턴했습니다
	// Request로 첨부파일 수정 여부 주시면 됩니다 (여기서는 String 'Y', 'N'으로 써놨어요)
	@Transactional
	@PutMapping("/class")
	public ResponseEntity<ClassBoard> updateBoard(
			ClassBoard body, 
			HttpServletRequest req, 
			@RequestPart(value="files", required = false) List<MultipartFile> files) 
	throws Exception{
		String school = body.getSchool();		
		int grade = body.getGrade();
		int classes = body.getClasses();
		int id = body.getId();
		ClassBoard board = classBoardRepository.findBySchoolAndGradeAndClassesAndId(school, grade, classes, id);
		
		// 첨부파일 수정 여부
		String fileIsChanged = req.getParameter("fileIsChanged");
		if (fileIsChanged.equals("Y")) {
			// 기존에 존재하던 첨부파일 모두 삭제
			classBoardFileRepository.deleteFile(id);
			// 현재 추가하는 첨부파일 저장
			String rootPath = request.getSession().getServletContext().getRealPath("/uploads");
			String resourcesPath = rootPath.substring(0, rootPath.length()-14) + "resources\\static\\uploads";	
			rootPath = resourcesPath;
			ClassBoard sameBoard = boardService.addBoard(board, files, rootPath);
			System.out.println("첨부파일 저장 됐어");
		}
		// 수정 내용 저장
		String category = body.getCategory();
		String title = body.getTitle();
		String content = body.getContent();
		board.update(category, title, content);
		System.out.println("글 수정 됐어");
		return new ResponseEntity<ClassBoard>(board, HttpStatus.OK);
	}
//	// 테스트 : 로컬 폴더에 파일 업로드 - 잘 작동
//	@PostMapping("/upload")
//    public ResponseEntity upload(@RequestPart MultipartFile file) {
////		String rootPath = request.getSession().getServletContext().getRealPath("/resources/uploadFiles/");
////		String rootPath = request.getSession().getServletContext().getRealPath("/");
//		String rootPath = request.getServletContext().getRealPath("/");
//		System.out.println(rootPath);
////		String savePath = rootPath + file.getOriginalFilename();
////		System.out.println(savePath);
////		String originalFileName = file.getOriginalFilename();
//        File uploadFile = new File(rootPath + file.getOriginalFilename());
////        try {
////            file.transferTo(destination);
////        } catch (IOException e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(originalFileName);
////        }
//        return ResponseEntity.status(HttpStatus.CREATED).body(file.getOriginalFilename());
//    }
}
